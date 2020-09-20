package com.springboot.java.entity;

import javax.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id = 0;

    @Column(name = "profile_name")
    private String profileName;

    @Column(name = "mailbox_size")
    private long mailboxSize;

    public Profile() {
    }

    public Profile(int id, String profileName, long mailboxSize) {
        this.id = id;
        this.profileName = profileName;
        this.mailboxSize = mailboxSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public long getMailboxSize() {
        return mailboxSize;
    }

    public void setMailboxSize(long mailboxSize) {
        this.mailboxSize = mailboxSize;
    }
}
