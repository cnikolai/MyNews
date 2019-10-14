package com.nikolai.mynews;

import org.junit.Test;
 import org.junit.Test;import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
    /**
     * Unit tests for the EmailValidator logic.
     */
    public class NotificationValidatorTest {
        @Test
        public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
            assertTrue(EmailValidator.isValidDate(""));
        }

        //mBeginDateViewText = findViewById(R.id.begin_date_txt);

        @Test
        public void emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
            assertTrue(EmailValidator.isValidDate("name@email.co.uk"));
        }
        @Test
        public void emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
            assertFalse(EmailValidator.isValidDate("name@email..com"));
        }
    }
}
