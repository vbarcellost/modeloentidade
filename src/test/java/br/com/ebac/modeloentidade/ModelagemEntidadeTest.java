package br.com.ebac.modeloentidade;

import br.com.ebac.modeloentidade.domain.Curso;
import br.com.ebac.modeloentidade.domain.Matricula;
import br.com.ebac.modeloentidade.domain.StatusMatricula;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ModelagemEntidadeTest {

    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    static void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("modelo-entidade");
    }

    @AfterAll
    static void tearDown() {
        entityManagerFactory.close();
    }

    @Test
    void deveCriarTabelasDasEntidades() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Number quantidadeTabelas = (Number) entityManager
                .createNativeQuery("""
                        select count(*)
                        from information_schema.tables
                        where table_name in ('TB_CURSO', 'TB_MATRICULA')
                        """)
                .getSingleResult();

        entityManager.close();

        assertEquals(2, quantidadeTabelas.intValue());
    }

    @Test
    void devePersistirCursoComMatriculaRelacionada() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Curso curso = new Curso();
        curso.setCodigo("JAVA");
        curso.setNome("Backend Java");
        curso.setDescricao("Curso de desenvolvimento backend com Java");

        Matricula matricula = new Matricula();
        matricula.setCodigo("MAT-001");
        matricula.setDataMatricula(LocalDate.now());
        matricula.setValor(new BigDecimal("1500.00"));
        matricula.setStatus(StatusMatricula.ATIVA);

        curso.adicionarMatricula(matricula);

        entityManager.getTransaction().begin();
        entityManager.persist(curso);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Matricula matriculaSalva = entityManager
                .createQuery("""
                        select m
                        from Matricula m
                        join fetch m.curso
                        where m.codigo = :codigo
                        """, Matricula.class)
                .setParameter("codigo", "MAT-001")
                .getSingleResult();

        assertNotNull(matriculaSalva.getId());
        assertNotNull(matriculaSalva.getCurso().getId());
        assertEquals("Backend Java", matriculaSalva.getCurso().getNome());
        assertEquals(1, matriculaSalva.getCurso().getMatriculas().size());

        entityManager.close();
    }
}
