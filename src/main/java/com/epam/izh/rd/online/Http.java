package com.epam.izh.rd.online;

import com.epam.izh.rd.online.entity.Pokemon;
import com.epam.izh.rd.online.service.PokemonFetchingServiceImpl;
import com.epam.izh.rd.online.service.PokemonFightingClubServiceImpl;

public class Http {
    public static void main(String[] args) {
        PokemonFetchingServiceImpl pokemonFetching = new PokemonFetchingServiceImpl();
        Pokemon pikachu = pokemonFetching.fetchByName("pikachu");
        Pokemon slowpoke = pokemonFetching.fetchByName("slowpoke");

        PokemonFightingClubServiceImpl fightingClub = new PokemonFightingClubServiceImpl();
        Pokemon winner = fightingClub.doBattle(pikachu, slowpoke);
        fightingClub.showWinner(winner);
    }
}
