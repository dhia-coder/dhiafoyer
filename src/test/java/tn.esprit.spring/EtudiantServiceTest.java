package tn.esprit.spring;

import org.junit.jupiter.api.extension.ExtendWith;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.Services.Etudiant.EtudiantService;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class EtudiantServiceTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantService etudiantService;

    private Etudiant etudiant;

    @BeforeEach
    public void setUp() {
        // Initialisation d'un objet Etudiant pour les tests
        etudiant = Etudiant.builder()
                .nomEt("Doe")
                .prenomEt("John")
                .cin(12345678)
                .ecole("Ecole Nationale")
                .dateNaissance(LocalDate.of(2000, 1, 1))
                .build();
    }

    @Test
    public void testCreateEtudiant() {
        // Mock de la méthode save pour simuler l'enregistrement de l'étudiant
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // Appel de la méthode du service
        Etudiant createdEtudiant = etudiantService.addOrUpdate(etudiant);

        // Vérification que la méthode save a été appelée et que l'objet retourné est valide
        verify(etudiantRepository, times(1)).save(etudiant);
        assertThat(createdEtudiant).isNotNull();
        assertThat(createdEtudiant.getNomEt()).isEqualTo("Doe");
    }

    @Test
    public void testDeleteEtudiant() {
        // Mock de la méthode deleteById pour simuler la suppression
        doNothing().when(etudiantRepository).deleteById(1L);

        // Appel de la méthode du service
        etudiantService.deleteById(1L);

        // Vérification que la méthode deleteById a été appelée
        verify(etudiantRepository, times(1)).deleteById(1L);
    }

    // Méthode simple pour tester la récupération d'un étudiant par son identifiant
    @Test
    public void testFindEtudiantById() {
        // Mock de la méthode findById pour simuler la récupération d'un étudiant
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        // Appel de la méthode du service
        Optional<Etudiant> foundEtudiant = Optional.ofNullable(etudiantService.findById(1L));

        // Vérification que la méthode findById a été appelée
        verify(etudiantRepository, times(1)).findById(1L);
        assertThat(foundEtudiant).isPresent();
        assertThat(foundEtudiant.get().getNomEt()).isEqualTo("Doe");
    }

    // Méthode simple pour tester la mise à jour d'un étudiant
    @Test
    public void testUpdateEtudiant() {
        // Modification de l'objet étudiant avant mise à jour
        etudiant.setNomEt("Updated Name");

        // Mock de la méthode save pour simuler la mise à jour de l'étudiant
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        // Appel de la méthode du service
        Etudiant updatedEtudiant = etudiantService.addOrUpdate(etudiant);

        // Vérification que la méthode save a été appelée et que le nom a été mis à jour
        verify(etudiantRepository, times(1)).save(etudiant);
        assertThat(updatedEtudiant.getNomEt()).isEqualTo("Updated Name");
    }
}
