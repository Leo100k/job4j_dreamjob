package dreamjob.repository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import dreamjob.configuration.DatasourceConfiguration;
import dreamjob.model.User;

import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class Sql2oUserRepositoryTest {

    private static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oVacancyRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);

    }

    @AfterEach
    public void clearUser() {
        var users = sql2oUserRepository.findAll();
        for (var user : users) {
            sql2oUserRepository.deleteById(user.getId());
        }
    }

    @Test
    public void whenSaveThanException() {
        var user = sql2oUserRepository.save(new User(0, "le500@mail.ru", "YYYYYY", "zzzzz"));
        var user2 = sql2oUserRepository.save(new User(0, "le500@mail.ru", "YYYYYY", "zzzzz"));
        assertThat(user2).isEqualTo(Optional.empty());
    }

    @Test
    public void findByEmailAndPassword() {
        var user = sql2oUserRepository.save(new User(0, "le300@mail.ru", "Лунтик", "zzzzz"));
        var faundUser = sql2oUserRepository.findByEmailAndPassword("le300@mail.ru", "zzzzz");
        assertThat(user).usingRecursiveComparison().isEqualTo(faundUser);
    }

}