
/*
 *     NecronomiBot. A Discord Bot for use with RPGs (RolePlaying Games)
 *     Copyright (C) 2020  Anderson dos Santos Silva
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.andersonstv.character;

import io.github.andersonstv.util.DiceUtil;
import io.github.andersonstv.util.ImportUtil;

public class CoCCharacter extends GenericCharacter {

    public CoCCharacter(String charName){
        super(charName);
        setDefault();
    }
    public void setDefault() {
        attributes = ImportUtil.importMapCSV(ImportUtil.filepath + "DEFAULT_ATTRIBUTES_CoC.csv");
        skills = ImportUtil.importMapCSV(ImportUtil.filepath + "DEFAULT_SKILLS_CoC.csv");

    }
    public String skillCheck(String skillName){
        if (skills.containsKey(skillName)){
            return DiceUtil.cocRoll10s(skills.get(skillName));
        } else {
            return "Skill not found.";
        }
    }

    public String attributeCheck(String attName){
        if (attributes.containsKey(attName)){
            return DiceUtil.cocRoll10s(attributes.get(attName));
        } else {
            return "Attribute not found.";
        }
    }
}
