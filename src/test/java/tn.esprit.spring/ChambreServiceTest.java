package tn.esprit.spring;

import org.junit.jupiter.api.extension.ExtendWith;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.Services.Chambre.ChambreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ChambreServiceTest {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreService chambreService;

    private Chambre chambre;
    private Bloc bloc;
    private Reservation reservation;

    @BeforeEach
    public void setUp() {
        // Initialisation des objets nécessaires pour le test
        bloc = Bloc.builder()
                .idBloc(1L)
                .nomBloc("Bloc A")
                .build();

        reservation = Reservation.builder()
                .idReservation("R1")
                .build();

        chambre = Chambre.builder()
                .numeroChambre(101)
                .typeC(TypeChambre.DOUBLE.SIMPLE)
                .bloc(bloc)
                .build();
    }

    @Test
    public void testCreateChambre() {
        // Mock de la méthode save pour simuler l'enregistrement de la chambre
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        // Appel de la méthode du service
        Chambre createdChambre = chambreService.addOrUpdate(chambre);

        // Vérification que la méthode save a été appelée et que l'objet retourné est valide
        verify(chambreRepository, times(1)).save(chambre);
        assertThat(createdChambre).isNotNull();
        assertThat(createdChambre.getNumeroChambre()).isEqualTo(101);
    }

//    @Test
//    public void testFindChambreById() {
//        // Mock de la méthode findById pour simuler la recherche d'une chambre
//        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));
//
//        // Appel de la méthode du service
//        Optional<Chambre> foundChambre = chambreService.findById(1L);
//
//        // Vérification que la méthode findById a été appelée et que la chambre a bien été trouvée
//        verify(chambreRepository, times(1)).findById(1L);
//        assertThat(foundChambre).isPresent();
//        assertThat(foundChambre.get().getNumeroChambre()).isEqualTo(101);
//    }

    @Test
    public void testDeleteChambre() {
        // Mock de la méthode deleteById pour simuler la suppression
        doNothing().when(chambreRepository).deleteById(1L);

        // Appel de la méthode du service
        chambreService.deleteById(1L);

        // Vérification que la méthode deleteById a bien été appelée
        verify(chambreRepository, times(1)).deleteById(1L);
    }

    // Méthode pour tester la récupération des chambres par bloc

}
