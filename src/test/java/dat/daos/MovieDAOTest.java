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
        int expectedSize = 84; // Example expected size
        int actualSize = allMovies.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    @DisplayName("Test Find Movie By Id")
    void testFindMovieById() {
        Movie movie = dao.findById(Movie.class, 1);
        assertNotNull(movie);

        // Expected and actual values
        String expectedTitle = "Speak No Evil"; // Example expected title
        String actualTitle = movie.getTitle();

        assertEquals(expectedTitle,actualTitle);
    }

    @Test
    @DisplayName("Update Movie")
    void testUpdateMovie() {
        Movie movie = dao.findById(Movie.class, 1);
        assertNotNull(movie);

        // Expected and actual values
        String expectedTitle = "Speak No Evil"; // Example expected title
        String actualTitle = movie.getTitle();

        assertEquals(expectedTitle,actualTitle);

        movie.setTitle("Speak No Evil 2");
        dao.update(movie);

        Movie updatedMovie = dao.findById(Movie.class, 1);
        assertNotNull(updatedMovie);

        // Expected and actual values
        String expectedUpdatedTitle = "Speak No Evil 2"; // Example expected title
        String actualUpdatedTitle = updatedMovie.getTitle();

        assertEquals(expectedUpdatedTitle,actualUpdatedTitle);
    }

}