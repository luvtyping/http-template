package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Pokemon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PokemonFightingClubServiceImpl implements PokemonFightingClubService {
    @Override
    public Pokemon doBattle(Pokemon p1, Pokemon p2) {
        boolean p1Turn = false;
        if (p1.getPokemonId() > p2.getPokemonId())
            p1Turn = true;
        while (p1.getHp() > 0 && p2.getHp() > 0) {
            if (p1Turn) {
                doDamage(p1, p2);
                p1Turn = false;
            } else {
                doDamage(p2, p1);
                p1Turn = true;
            }
        }
        return p1.getHp() > 1 ? p1 : p2;
    }

    @Override
    public void showWinner(Pokemon winner) {
        byte[] imageBytes = new PokemonFetchingServiceImpl().getPokemonImage(winner.getPokemonName());
        try {
            BufferedImage image = ImageIO.read(new URL(new String(imageBytes)));
            ImageIO.write(image, "png", new File("src/images", winner.getPokemonName() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doDamage(Pokemon from, Pokemon to) {
        int damage = (from.getAttack() - from.getAttack() * to.getDefense() / 100);
        to.setHp((short) (to.getHp() - damage));
    }
}