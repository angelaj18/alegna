import java.util.List;

public interface GameWorldFactory {
    List<City> createCities();

    List<Hero> createHeroes();
}
