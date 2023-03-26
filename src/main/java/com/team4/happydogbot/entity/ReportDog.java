package com.team4.happydogbot.entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;

/**
 * Класс, описывающий отчеты пользователей о состоянии животного
 * @param 'examination' поле для статуса проверки (выполняется волонтером) отчета (по умолчанию - null)
 * @see AdopterDog
 * @see Dog
 * @see ReportDog
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "report_dog")
public class ReportDog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @CreationTimestamp
    @Column(name = "report_date")
    private LocalDate reportDate;
    @Column(name = "file_id")
    private String fileId;
    @Column(name = "report_text", length = 1024)
    private String caption;
    @Column(name = "examination")
    private Boolean examination = null;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    @JsonBackReference
    private AdopterDog adopterDog;

    public ReportDog(Long id, LocalDate reportDate, String fileId, String caption) {
        this.id = id;
        this.reportDate = reportDate;
        this.fileId = fileId;
        this.caption = caption;
    }
}
