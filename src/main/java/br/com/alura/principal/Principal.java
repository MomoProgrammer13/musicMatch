package br.com.alura.principal;

import br.com.alura.model.Artista;
import br.com.alura.model.Musicas;
import br.com.alura.model.TipoArtista;
import br.com.alura.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    private Scanner leitura = new Scanner(System.in);

    @Autowired
    private ArtistaRepository repositorio;
    public void exibeMenu(){
        var opcao = 1;

        while (opcao != 0){
            var menu = """
                    1 - Cadastrar artistas
                    2 - Cadastrar músicas
                    3 - Listar músicas
                    4 - Buscar músicas por artistas
                    
                    0 - Sair
                    """;
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();
            switch (opcao) {
                case 1:
                    cadastrarArtista();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    listarMusicasPorArtista();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }                    
        }
    }

    private void cadastrarArtista() {
        System.out.println("Infrome o nome desse artista: ");
        var nome = leitura.nextLine();
        System.out.println("Informe o tipo desse artista: (solo, dupla, banda)");
        var tipo = TipoArtista.fromString(leitura.nextLine());
        Artista artista = new Artista();
        artista.setNome(nome);
        artista.setTipoArtista(tipo);
        repositorio.save(artista);
        System.out.println("Cadastrar outro artista? (S/N)");
        if(leitura.nextLine().equalsIgnoreCase("s")){
            cadastrarArtista();
        }

    }

    private void cadastrarMusicas() {
        List<Artista> artistas = repositorio.findAll();
        artistas.forEach(a -> System.out.println(a.getNome()));
        System.out.println("A musica pertence a qual Artista? ");
        var nomeArtista = leitura.nextLine();
        Optional<Artista> artista = artistas.stream().filter( a -> a.getNome().toLowerCase().contains(nomeArtista.toLowerCase())).findFirst();
        if(artista.isPresent()){
            var artistaEncontrado = artista.get();
            System.out.println("Digite o nome da Musica");
            var nomeMusica = leitura.nextLine();
            Musicas musica = new Musicas();
            musica.setTitulo(nomeMusica);
            musica.setArtista(artistaEncontrado);
            artistaEncontrado.getMusicas().add(musica);
            repositorio.save(artistaEncontrado);
        } else{
            System.out.println("Artista não encontrado");
        }
    }

    private void listarMusicas() {
        List<Musicas> musicas = repositorio.listarTodasAsMusicas();
        musicas.forEach(m -> System.out.println("Titulo: " + m.getTitulo() + " Artista: " + m.getArtista().getNome()));
    }

    private void listarMusicasPorArtista() {
        List<Artista> artistas = repositorio.findAll();
        artistas.forEach(a -> System.out.println(a.getNome()));
        System.out.println("Qual artista deseja ver as musicas? ");
        var nomeArtista = leitura.nextLine();
        Optional<Artista> artista = artistas.stream().filter( a -> a.getNome().toLowerCase().contains(nomeArtista.toLowerCase())).findFirst();
        if(artista.isPresent()){
            var artistaEncontrado = artista.get();
            List<Musicas> musicas = repositorio.listarMusicasPorArtista(artistaEncontrado);
            musicas.forEach(m -> System.out.println("Titulo: " + m.getTitulo() + " Artista: " + m.getArtista().getNome()));

        }
    }

}
