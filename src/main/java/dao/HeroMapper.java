package dao;

import pojo.Hero;

import java.util.List;

public interface HeroMapper {
    List<Hero> findAllHeroes();
    Hero findHero(int id);
    boolean deleteHero(int id);
    boolean updateHero(Hero hero);
    boolean insertHero(Hero hero);
    Hero findHeroByName(String name);
    List<Hero> searchHero(String term);
}
