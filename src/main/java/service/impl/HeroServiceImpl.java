package service.impl;

import dao.HeroMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Hero;
import service.HeroService;

import java.util.List;

@Service
public class HeroServiceImpl implements HeroService {
    @Autowired
    HeroMapper heroMapper;

    @Override
    public List<Hero> findAllHeroes() {
        return heroMapper.findAllHeroes();
    }

    @Override
    public Hero findHero(int id) {
        return heroMapper.findHero(id);
    }

    @Override
    public boolean deleteHero(int id) {
        return heroMapper.deleteHero(id);
    }

    @Override
    public boolean updateHero(Hero hero) {
        return heroMapper.updateHero(hero);
    }

    @Override
    public boolean insertHero(Hero hero) {
        return heroMapper.insertHero(hero);
    }

    @Override
    public Hero findHeroByName(String name){return heroMapper.findHeroByName(name);}

    @Override
    public List<Hero> searchHero(String term){return heroMapper.searchHero(term);}
}
