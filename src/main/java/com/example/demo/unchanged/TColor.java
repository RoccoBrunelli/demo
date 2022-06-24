package com.example.demo.unchanged;
import java.io.Serializable;

/**
 * Enumerator that contains possible colors of towers
 */
public enum TColor implements Serializable {

    /**
     * Used black towers
     */
    BLACK {
        @Override
        public String toString() {
            return "BLACK";
        }
    },
    /**
     * Used for gray towers
     */
    GREY {
        @Override
        public String toString() {
            return "GREY";
        }
    },
    /**
     * Used for with towers
     */
    WHITE {
        @Override
        public String toString() {
            return "WHITE";
        }
    }
}
