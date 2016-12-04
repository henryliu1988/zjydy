package com.zhjydy.model.entity;

/**
 * Created by Administrator on 2016/9/23 0023.
 */
public class District {

    String Spell = "";
    String id = "";
    String name = "";
    String Path = "";
    String parentid = "";
    String Depth = "";
    String SpellShort = "";
    String listorder = "";
    String aid = "";



    public String getSpell() {
        return Spell;
    }

    public void setSpell(String spell) {
        Spell = spell;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getDepth() {
        return Depth;
    }

    public void setDepth(String depth) {
        Depth = depth;
    }

    public String getSpellShort() {
        return SpellShort;
    }

    public void setSpellShort(String spellShort) {
        SpellShort = spellShort;
    }

    public String getListorder() {
        return listorder;
    }

    public void setListorder(String listorder) {
        this.listorder = listorder;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
