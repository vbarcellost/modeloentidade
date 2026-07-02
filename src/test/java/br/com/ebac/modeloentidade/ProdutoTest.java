package br.com.ebac.modeloentidade;

import br.com.ebac.modeloentidade.domain.Produto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProdutoTest {

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
    void deveCriarTabelaProduto() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Number quantidadeTabelas = (Number) entityManager
                .createNativeQuery("""
                        select count(*)
                        from information_schema.tables
                        where table_name = 'TB_PRODUTO'
                        """)
                .getSingleResult();

        entityManager.close();

        assertEquals(1, quantidadeTabelas.intValue());
    }

    @Test
    void deveCadastrarProduto() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        Produto produto = new Produto();
        produto.setCodigo("P001");
        produto.setNome("Notebook");
        produto.setDescricao("Notebook para desenvolvimento Java");
        produto.setValor(new BigDecimal("3500.00"));

        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Produto produtoSalvo = entityManager
                .createQuery("select p from Produto p where p.codigo = :codigo", Produto.class)
                .setParameter("codigo", "P001")
                .getSingleResult();

        entityManager.close();

        assertNotNull(produtoSalvo.getId());
        assertEquals("Notebook", produtoSalvo.getNome());
        assertEquals(new BigDecimal("3500.00"), produtoSalvo.getValor());
    }
}
