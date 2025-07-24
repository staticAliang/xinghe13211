package com.fengshen.server.data.game;

public class SuitEffectUtils {
    public static int[] suit(int sex, int attrib, int polar, int eq_polar) {
        int[] suit_light_effects = { 7001, 7002, 7003, 7004, 7005 };
        int[] effect_suit = { 0, suit_light_effects[eq_polar - 1] };
            if (attrib <= 79) {
            int[][] suit_icons = { { 860701, 870702, 870703, 860704, 860705 }, { 870701, 860702, 860703, 870704, 870705 } };
            effect_suit[0] = suit_icons[sex][polar - 1];
        } else if (attrib <= 89) {
            int[][] suit_icons = { { 860801, 870802, 870803, 860804, 860805 }, { 870801, 860802, 860803, 870804, 870805 } };
            effect_suit[0] = suit_icons[sex][polar - 1];
        } else if (attrib <= 99) {
            int[][] suit_icons = { { 860901, 870902, 870903, 860904, 860905 }, { 870901, 860902, 860903, 870904, 870905 } };
            effect_suit[0] = suit_icons[sex][polar - 1];
        } else if (attrib <= 109) {
            int[][] suit_icons = { { 861001, 871002, 871003, 861004, 861005 }, { 871001, 861002, 861003, 871004, 871005 } };
            effect_suit[0] = suit_icons[sex][polar - 1];
        } else if (attrib <= 119) {
            int[][] suit_icons = { { 861101, 871102, 871103, 861104, 861105 }, { 871101, 861102, 861103, 871104, 871105 } };
            effect_suit[0] = suit_icons[sex][polar - 1];
        } else if (attrib <= 129) {
            int[][] suit_icons = { { 861201, 871202, 871203, 861204, 861205 }, { 871201, 861202, 861203, 871204, 871205 } };
            effect_suit[0] = suit_icons[sex][polar - 1];
        } else if (attrib <= 139) {
            int[][] suit_icons = { { 861301, 871302, 871303, 861304, 861305 }, { 871301, 861302, 861303, 871304, 871305 } };
            effect_suit[0] = suit_icons[sex][polar - 1];
        } else if (attrib <= 149) {
            int[][] suit_icons = { { 861401, 871402, 871403, 861404, 861405 }, { 871401, 861402, 861403, 871404, 871405 } };
            effect_suit[0] = suit_icons[sex][polar - 1];
        } else if (attrib <= 159) {
            int[][] suit_icons = { { 861501, 871502, 871503, 861504, 861505 }, { 871501, 861502, 861503, 871504, 871505 } };
            effect_suit[0] = suit_icons[sex][polar - 1];
        } else if (attrib <= 169) {
            int[][] suit_icons = { { 861601, 871602, 871603, 861604, 861605 }, { 871601, 861602, 861603, 871604, 871605 } };
            effect_suit[0] = suit_icons[sex][polar - 1];
        } else if (attrib <= 179) {
            int[][] suit_icons = { { 861701, 871702, 871703, 861704, 861705 }, { 871701, 861702, 861703, 871704, 871705 } };
            effect_suit[0] = suit_icons[sex][polar - 1];


        }
        return effect_suit;

    }

}
