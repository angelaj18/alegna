import java.util.ArrayList;
import java.util.List;

public class DefaultGameWorldFactory implements GameWorldFactory {

    @Override
    public List<City> createCities() {
        List<City> cities = new ArrayList<>();
        cities.add(new City("Skyline Bay", 75, 70));
        cities.add(new City("Metro Vale", 80, 65));
        return cities;
    }

    @Override
    public List<Hero> createHeroes() {
        List<Hero> heroes = new ArrayList<>();
        heroes.add(new Hero("Nova Shield", 88));
        heroes.add(new Hero("Velocity Arc", 72));
        heroes.add(new Hero("Titan Vale", 93));
        heroes.add(new Hero("Echo Warden", 67));
        return heroes;
    }
}
