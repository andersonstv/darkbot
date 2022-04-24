package io.github.andersonstv.character;


import io.github.andersonstv.util.DiceUtil;
import io.github.andersonstv.util.FormatUtil;
import io.github.andersonstv.util.ImportUtil;
import org.apache.commons.text.WordUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class WoDCharacter{
    private String id;
    private Map<String, Integer> attributes;
    private Map<String, Integer> skills;
    private Map<String, String> descriptions;
    private int[] hp;
    private int[] willpower;

    public WoDCharacter(String charName) {
        this.id = charName;
        attributes = new LinkedHashMap<>();
        skills = new LinkedHashMap<>();
        descriptions = new LinkedHashMap<>();
        setDefault();
    }
    public String getId() {
        return id;
    }

    public Map<String, Integer> getAttributes() {
        return attributes;
    }

    public Map<String, Integer> getSkills() {
        return skills;
    }

    public Map<String, String> getDescriptions() {
        return descriptions;
    }


    public void removeStat(String statName){
        attributes.remove(statName);
        skills.remove(statName);
    }
    public String getStat(String statName){
        if (attributes.containsKey(statName)){
            return statName + attributes.get(statName);
        } else if (skills.containsKey(statName)){
            return statName + skills.get(statName);
        }
        return "Not found.";
    }
    public Integer getAttribute(String attName){
        return attributes.get(attName);
    }
    public void addDescription(String descriptionName, String value){
        descriptions.put(descriptionName, value);
    }
    public String getDescription(String descriptionName){
        return descriptions.get(descriptionName);
    }
    public void setDefault(){
        attributes = ImportUtil.importMapCSV(ImportUtil.filepath + "DEFAULT_ATTRIBUTES_WoD.csv");
        skills = ImportUtil.importMapCSV(ImportUtil.filepath + "DEFAULT_SKILLS_WoD.csv");
        calculateStats();
    }
    public void calculateStats(){
        hp = new int[]{ attributes.get("size") + attributes.get("stamina"),0};
        willpower = new int[]{attributes.get("composure") + attributes.get("resolve"), 0};
    }


    public void setStat(String statName, int value){
        if (attributes.containsKey(statName)){
            attributes.put(statName, value);
        } else{
            skills.put(statName, value);
        }
        calculateStats();
    }
    public String check(String stat){
        if (attributes.containsKey(stat)){
            return attributeCheck(stat);
        } else if(skills.containsKey(stat)){
            return skillCheck(stat);
        } else {
            return "Skill or Attribute not found.";
        }
    }
    public String check(String stat, String secondary){
        int total;
        if (attributes.containsKey(stat)){
            if(attributes.containsKey(secondary)){
                total = attributes.get(stat) + attributes.get(secondary);
                return DiceUtil.wodRoll(total);
            } else if(skills.containsKey(secondary)){
                total = attributes.get(stat) + skills.get(secondary);
                return DiceUtil.wodRoll(total);

            }
        } else if(skills.containsKey(stat)){
            if(attributes.containsKey(secondary)){
                total = skills.get(stat) + attributes.get(secondary);
                return DiceUtil.wodRoll(total);
            } else if(skills.containsKey(secondary)){
                total = skills.get(stat) + skills.get(secondary);
                return DiceUtil.wodRoll(total);
            }
        }
        return "Skill or Attribute Not found.";
    }
    private String skillCheck(String skillName){
        return DiceUtil.wodRoll(skills.get(skillName), 8);
    }
    private String attributeCheck(String attName){
        return DiceUtil.wodRoll(attributes.get(attName));
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WoDCharacter that = (WoDCharacter) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        String sep = FormatUtil.sep;
        StringBuilder sheet = new StringBuilder();
        sheet.append("**Name: ***").append(id).append("*").append(sep);
        sheet.append("**Health: **").append(hp[0]).append("[").append(hp[1]).append("]").append(sep);
        sheet.append("**Willpower: **").append(willpower[0]).append("[").append(willpower[1]).append("]").append(sep);
        sheet.append("**Attributes: **").append(sep);
        for (Map.Entry<String, Integer> entry : attributes.entrySet()) {
            sheet.append(WordUtils.capitalize(entry.getKey())).append(": ").append(entry.getValue()).append(sep);
        }
        sheet.append("**Skills: **").append(sep);
        for (Map.Entry<String, Integer> entry : skills.entrySet()) {
            sheet.append(WordUtils.capitalizeFully(entry.getKey())).append(": ").append(entry.getValue()).append(sep);
        }
        return sheet.toString();
    }
}
