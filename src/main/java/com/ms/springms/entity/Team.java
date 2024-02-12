    package com.ms.springms.entity;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.List;

    @Entity
    @Table(name = "team")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Team {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "name_team")
        private String nameTeam;

        @Column(name ="id_user")
        private Long idUser;

        @ManyToOne
        @JoinColumn(name = "id_award")
        private Awards awards;

        @OneToMany(mappedBy = "team")
        @JsonIgnore // Menambahkan anotasi @JsonIgnore untuk menghindari rekursi tak terbatas
        private List<MemberTeam> members;


        public Long getIdAward() {
            return awards != null ? awards.getId() : null;
        }

        public void setIdAward(Long idAward) {
            if (awards == null) {
                awards = new Awards();
            }
            awards.setId(idAward);
        }




    }
