package com.example.demo.unchanged;
import com.example.demo.unchanged.Containers;
import com.example.demo.unchanged.Modifier;
import com.example.demo.unchanged.Personality;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.unchanged.Constants.CONTAINERS_PERSONALITIES;
import static com.example.demo.unchanged.Constants.PERSONALITIES_STARTING_PRICE;

/**
 * Factory pattern that allows creation of both Modifier and Containers
 */
public class PersonalityFactory {
    /**
     * Generates personality (of type Containers of Modifier) based on its name
     * @param personalityName name of personality
     * @return Container or Modifier
     * @throws RuntimeException if name of personality isn't in the list of personalities
     */
    public static Personality generate(String personalityName) throws RuntimeException{
        if(CONTAINERS_PERSONALITIES.containsKey(personalityName)){
            return new Containers(personalityName);
        } else if (PERSONALITIES_STARTING_PRICE.keySet().contains(personalityName)){
            return new Modifier(personalityName);
        } else {
            throw new RuntimeException("Personality name not existing: " + personalityName);
        }
    }

    /**
     * Generates a list of personalities based on a list of names
     * @param personalityNames list of names of personalities
     * @return list of Personalities
     * @throws RuntimeException if a name in the list isn't in the list of personalities
     */
    public static List<Personality> generate(List<String> personalityNames) throws  RuntimeException{
        List<Personality> personalities = new ArrayList<Personality>();
        for(String p : personalityNames){
            personalities.add(generate(p));
        }
        return personalities;
    }
}
