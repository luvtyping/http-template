package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.entity.Pokemon;
import com.epam.izh.rd.online.factory.ObjectMapperFactoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

public class PokemonFetchingServiceImpl implements PokemonFetchingService {
    @Override
    public Pokemon fetchByName(String name) {
        String query = "https://pokeapi.co/api/v2/pokemon/" + name;
        HttpURLConnection connection = getHttpURLConnection(query);
        String json = getJson(connection);
        ObjectMapper mapper = new ObjectMapperFactoryImpl().getObjectMapper();
        return getPokemonWithJsonProperties(mapper, new Pokemon(), json);
    }

    @Override
    public byte[] getPokemonImage(String name) throws IllegalArgumentException {
        String query = "https://pokeapi.co/api/v2/pokemon/" + name;
        HttpURLConnection connection = getHttpURLConnection(query);
        String json = getJson(connection);
        ObjectMapper mapper = new ObjectMapperFactoryImpl().getObjectMapper();
        byte[] bytes = new byte[0];
        try {
            JsonNode jsonNode = mapper.readTree(json);
            bytes = jsonNode.get("sprites").get("front_default").asText().getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private static HttpURLConnection getHttpURLConnection(String query) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(query).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-agent", "User");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return connection;
    }

    private static String getJson(HttpURLConnection connection) {
        String json = "";
        try {
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode())
                json = new BufferedReader(new InputStreamReader(connection.getInputStream()))
                        .lines().collect(Collectors.joining("\n"));
            else throw new IllegalArgumentException(String.valueOf(connection.getResponseCode()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    private Pokemon getPokemonWithJsonProperties(ObjectMapper mapper, Pokemon pokemon, String json) {
        try {
            pokemon = mapper.readValue(json, Pokemon.class);
            JsonNode statsNode = mapper.readTree(json).get("stats");
            for (JsonNode jsonNode : statsNode) {
                switch (jsonNode.get("stat").get("name").asText()) {
                    case "hp":
                        pokemon.setHp(jsonNode.get("base_stat").shortValue());
                        break;
                    case "attack":
                        pokemon.setAttack(jsonNode.get("base_stat").shortValue());
                        break;
                    case "defense":
                        pokemon.setDefense(jsonNode.get("base_stat").shortValue());
                        break;
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return pokemon;
    }
}
