package com.yaktest.gerichte.gerichte;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/gerichte")
@RequiredArgsConstructor
public class GerichteController {

    private final GerichtService gerichtService;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping
    public ResponseEntity<List<Gericht>> getAllGerichte(Principal principal) throws IOException {
        List<Gericht> alleGerichte = gerichtService.openGerichte(principal.getName());
        return ResponseEntity.ok(alleGerichte);
    }

    @GetMapping("/my/my")
    public ResponseEntity<List<Gericht>> getMyGerichte(Principal principal) throws IOException {
        List<Gericht> alleGerichte = gerichtService.getGerichtebyAuthor(principal.getName());
        return ResponseEntity.ok(alleGerichte);
    }

    @GetMapping("/{authName}")
    public ResponseEntity<List<Gericht>> getMeineGericht(@PathVariable String authName){
        return ResponseEntity.ok(gerichtService.getGerichtebyAuthor(authName));
    }

    @PostMapping
    public ResponseEntity<Gericht> addGericht(@RequestBody Gericht request, Principal principal){
        request.setAuthor(principal.getName());
        return new ResponseEntity<Gericht>(gerichtService.addGericht(request), HttpStatus.CREATED);
    }

    @GetMapping("/my/myCount")
    public ResponseEntity<Optional<Integer>> getMyUserCount(Principal principal){
        return new ResponseEntity<Optional<Integer>>(gerichtService.countByAuthor(principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/{gerichtID}")
    public ResponseEntity<Gericht> deleteGericht(@PathVariable String gerichtID, Principal principal){
        Gericht gericht = gerichtService.singleGerichtById(gerichtID).get();
        if(gericht.getAuthor().equals(principal.getName())) {
            gerichtService.deleteGerichtbyId(gerichtID);
        }
        return new ResponseEntity<Gericht>(gericht, HttpStatus.OK);
    }

    @PostMapping("/my/myPlan")
    public ResponseEntity<List<Gericht>> createAPlan(@RequestBody PlanRequest request, Principal principal){
        // Set Cover Problem (leider NP-vollständig iirc), greedy ansatz
        // tests doch ganz gut
        List<Gericht> möglicheGerichte = gerichtService.getGerichtebyAuthor(principal.getName());
        List<Gericht> möglicheGerichteClone = new ArrayList<>();
        möglicheGerichteClone.addAll(möglicheGerichte);
        int days = request.getDays();
        List<GerichtZutat> zutaten = request.getZutaten();
        List<Gericht> planGerichte = new ArrayList<Gericht>();

        if(möglicheGerichte.size() == 0){
            return ResponseEntity.ok(planGerichte);
        }

        for(int i = 0;i < days;i++){
            final Gericht[] aktuellesGericht = {möglicheGerichte.get(0)};
            List<GerichtZutat> finalZutaten = zutaten;
            möglicheGerichte.forEach(gericht -> {
                List<String> zutatenToRemoveAktuell = aktuellesGericht[0].getZutaten().stream()
                        .map(GerichtZutat::getName)
                        .collect(Collectors.toList());
                List<String> zutatenToRemoveChallenger = gericht.getZutaten().stream()
                        .map(GerichtZutat::getName)
                        .collect(Collectors.toList());

                List<GerichtZutat> zutatenTestAktuell = finalZutaten.stream()
                        .filter(zutat -> !zutatenToRemoveAktuell.contains(zutat.getName()))
                        .collect(Collectors.toList());
                List<GerichtZutat> zutatenTestChallenger = finalZutaten.stream()
                        .filter(zutat -> !zutatenToRemoveChallenger.contains(zutat.getName()))
                        .collect(Collectors.toList());

                if(zutatenTestChallenger.size() < zutatenTestAktuell.size()){
                   aktuellesGericht[0] = gericht;
                }
            });

            möglicheGerichte.remove(aktuellesGericht[0]);
            if(möglicheGerichte.size() == 0){
                möglicheGerichte.addAll(möglicheGerichteClone);
            }
            List<String> zutatenToRemoveAktuell = aktuellesGericht[0].getZutaten().stream()
                    .map(GerichtZutat::getName)
                    .collect(Collectors.toList());
            zutaten = zutaten.stream()
                    .filter(zutat -> !zutatenToRemoveAktuell.contains(zutat.getName()))
                    .collect(Collectors.toList());
            // Gericht = GerichtResponse ändern?
            Gericht gericht = Gericht.builder()
                    .id(aktuellesGericht[0].getId())
                    .name(aktuellesGericht[0].getName())
                    .author(aktuellesGericht[0].getAuthor())
                    .zutaten(aktuellesGericht[0].getZutaten())
                    .anleitung(aktuellesGericht[0].getAnleitung())
                    .tags(aktuellesGericht[0].getTags())
                    .images(aktuellesGericht[0].getImages())
                    .build();
            planGerichte.add(gericht);

        }
        return ResponseEntity.ok(planGerichte);
    }
}
