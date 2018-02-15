package com.example.android.pets.data;

import android.provider.BaseColumns;

/**
 * Created by prayas on 10/2/18.
 */

public final class PetContract {
    public class PetEntry implements BaseColumns {

        public static final String TABLE_NAME = "pets";
        public static final String _ID = BaseColumns._ID;
        public static final String PET_NAME = "name";
        public static final String BREED_NAME ="breed";
        public static final String GENDER = "gender";
        public static final String WEIGHT = "weight";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }
}
