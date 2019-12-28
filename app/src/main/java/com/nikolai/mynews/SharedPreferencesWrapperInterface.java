package com.nikolai.mynews;

import java.util.Date;

public interface SharedPreferencesWrapperInterface {
    void saveSwitchState(boolean isChecked);

    void saveLastUpdate(Date lastUpdated);

    boolean getSaveSwitchState();

    Date getLastSeenUpdate();
}
