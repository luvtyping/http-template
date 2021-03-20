package com.epam.izh.rd.online.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Покемон. Поля должны заполняться из JSON, который возвратит внешний REST-service
 * Для маппинга значений из массива stats рекомендуется использовать отдельный класс Stat и аннотацию @JsonCreator
 */
@Data
@NoArgsConstructor
public class Pokemon {

    /**
     * Уникальный идентификатор, маппится из поля pokemonId
     */
    @JsonProperty("id")
    private long pokemonId;

    /**
     * Имя покемона, маппится из поля pokemonName
     */
    @JsonProperty("name")
    private String pokemonName;

    /**
     * Здоровье покемона, маппится из массива объектов stats со значением name: "hp"
     */
    private short hp;

    /**
     * Атака покемона, маппится из массива объектов stats со значением name: "attack"
     */
    private short attack;

    /**
     * Защита покемона, маппится из массива объектов stats со значением name: "defense"
     */
    private short defense;

    @JsonCreator
    public Pokemon(@JsonProperty("stats") Stat[] stats) {
        this.hp = stats[0].getStat();
        this.attack = stats[1].getStat();
        this.defense = stats[2].getStat();
    }
}
