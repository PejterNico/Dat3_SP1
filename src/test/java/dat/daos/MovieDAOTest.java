package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Movie;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class MovieDAOTest {
    static MovieDAO dao;
    static EntityManagerFactory emf;

    @BeforeAll
    static void setUpAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        dao = new MovieDAO();
    }

    @AfterAll
    static void tearDownAll() {
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Test All Movies")
    void testAllMovies() {
        List<Movie> allMovies = dao.findAll(Movie.class);
        assertNotNull(allMovies);

        // Expected and actual values
        int expectedSize = 82; // Example expected size
        int actualSize = allMovies.size();

        assertEquals(expectedSize, actualSize, "The number of movies should be " + expectedSize);
    }

    @Test
    @DisplayName("Test Find Movie By Id")
    void testFindMovieById() {
        Movie movie = dao.findById(Movie.class, 1);
        assertNotNull(movie);

        // Expected and actual values
        String expectedTitle = "Avatar"; // Example expected title
        String actualTitle = movie.getTitle();

        assertEquals(expectedTitle, actualTitle);
    }

}