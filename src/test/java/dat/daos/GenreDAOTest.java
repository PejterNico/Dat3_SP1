package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Genre;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenreDAOTest {
    static GenreDAO dao;
    static EntityManagerFactory emf;

    Genre genre1, genre2, genre3, genre4;

    @BeforeAll
    static void setUpAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        dao = new GenreDAO(emf);
    }

    @AfterAll
    static void tearDownAll() {
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    void setUp() {
        genre1 = new Genre();
        genre1.setName("Action");

        genre2 = new Genre();
        genre2.setName("Comedy");

        genre3 = new Genre();
        genre3.setName("Drama");

        genre4 = new Genre();
        genre4.setName("Horror");

        dao.create(genre1);
        dao.create(genre2);
        dao.create(genre3);
        dao.create(genre4);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCreateGenre() {
        Genre genre = new Genre();
        genre.setName("Action");
        dao.create(genre);
        assertNotNull(genre.getId());
    }

    @Test
    void testFindMoviesByGenre() {
        List<Movie> movies = dao.findMoviesByGenre("Action");
        assertNotNull(movies);
        assertTrue(movies.size() > 0);
    }

    @Test
        void testFindMovieById () {
        Genre genre = (Genre) dao.findById(Movie.class, 1);
        assertNotNull(genre);

        // Expected and actual values
        String expectedTitle = "Action"; // Example expected title
        String actualTitle = genre.getName();

        assertEquals(expectedTitle, actualTitle);
    }
}