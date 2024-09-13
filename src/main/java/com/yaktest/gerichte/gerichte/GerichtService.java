package com.yaktest.gerichte.gerichte;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GerichtService {

    private final GerichtRepository gerichtRepository;

    public List<Gericht> allGerichte() {
        return gerichtRepository.findAll();
    }

    public List<Gericht> openGerichte(String author) {
        return gerichtRepository.findGerichtByAuthorOrIsOpen(author);
    }
    public List<Gericht> getGerichtebyAuthor(String author){
        return gerichtRepository.findGerichtByAuthor(author);
    }
    public Optional<Gericht> singleGericht(String gerichtName){
        return gerichtRepository.findGerichtByName(gerichtName);
    }
    public Optional<Gericht> singleGerichtById(String id){
        return gerichtRepository.findGerichtById(id);
    }

    public Gericht addGericht(Gericht request){
        Gericht gericht = Gericht.builder()
                .name(request.getName())
                .zutaten(request.getZutaten())
                .author(request.getAuthor())
                .anleitung(request.getAnleitung())
                .tags(request.getTags())
                .isOpen(true)
                .images(request.getImages())
                .build();
        gerichtRepository.save(gericht);
        return gericht;
    }

    public Optional<Integer> countByAuthor(String author){
        return gerichtRepository.countByAuthor(author);
    }

    public void deleteGerichtbyId(String gerichtId){
        gerichtRepository.deleteById(gerichtId);
    }
}
