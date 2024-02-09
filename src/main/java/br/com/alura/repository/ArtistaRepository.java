package br.com.alura.repository;

import br.com.alura.model.Artista;
import br.com.alura.model.Musicas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtistaRepository extends JpaRepository<Artista,Long> {

    @Query("select m from Artista a join a.musicas m order by a.nome")
    List<Musicas> listarTodasAsMusicas();

    @Query("select m from Artista a join a.musicas m where a = :artistaEncontrado order by m.titulo")
    List<Musicas> listarMusicasPorArtista(Artista artistaEncontrado);
}
