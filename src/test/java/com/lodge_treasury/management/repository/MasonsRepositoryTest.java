package com.lodge_treasury.management.repository;

import com.lodge_treasury.management.config.TestAuditConfig;
import com.lodge_treasury.management.entity.Mason;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@Import(TestAuditConfig.class)
public class MasonsRepositoryTest extends AbstractRepositoryTest{

    @Autowired
    private MasonsRepository masonsRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Mason activeMason;
    private Mason deletedMason;

    @BeforeEach
    void setUp() {
        activeMason = new Mason();
        activeMason.setName("Active");
        activeMason.setLastName("Mason");
        activeMason.setDateOfBirth(LocalDate.of(1990, 1, 1));
        activeMason.setDeleted(false);
        activeMason = entityManager.persistAndFlush(activeMason);

        deletedMason = new Mason();
        deletedMason.setName("Deleted");
        deletedMason.setLastName("Mason");
        deletedMason.setDateOfBirth(LocalDate.of(1991, 2, 2));
        deletedMason.setDeleted(true);
        deletedMason = entityManager.persistAndFlush(deletedMason);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findByMasonIdAndDeletedFalse_shouldReturnActiveMason_whenExists() {
        Optional<Mason> found = masonsRepository.findByMasonIdAndDeletedFalse(activeMason.getMasonId());
        assertThat(found).isPresent();
        assertThat(found.get().getMasonId()).isEqualTo(activeMason.getMasonId());
        assertThat(found.get().getName()).isEqualTo(activeMason.getName());
        assertThat(found.get().getLastName()).isEqualTo(activeMason.getLastName());
        assertThat(found.get().getDateOfBirth()).isEqualTo(activeMason.getDateOfBirth());
        assertThat(found.get().isDeleted()).isFalse();
    }

    @Test
    void findByMasonIdAndDeletedFalse_shouldReturnEmpty_whenMasonIsDeleted() {
        Optional<Mason> found = masonsRepository.findByMasonIdAndDeletedFalse(deletedMason.getMasonId());
        assertThat(found).isEmpty();
    }

    @Test
    void findByMasonIdAndDeletedFalse_shouldReturnEmpty_whenNotFound() {
        Optional<Mason> found = masonsRepository.findByMasonIdAndDeletedFalse(999);
        assertThat(found).isEmpty();
    }

    @Test
    void findAllByDeletedFalse_shouldReturnOnlyActiveMasons() {
        List<Mason> activeMasons = masonsRepository.findAllByDeletedFalse();
        assertThat(activeMasons)
                .extracting(Mason::getMasonId)
                .contains(activeMason.getMasonId())
                .doesNotContain(deletedMason.getMasonId());
    }

    @Test
    void findById_shouldReturnDeletedMason_whenUsingUnfilteredMethod() {
        Optional<Mason> found = masonsRepository.findById(deletedMason.getMasonId());
        assertThat(found).isPresent();
        assertThat(found.get().getMasonId()).isEqualTo(deletedMason.getMasonId());
        assertThat(found.get().isDeleted()).isTrue();
    }
}
