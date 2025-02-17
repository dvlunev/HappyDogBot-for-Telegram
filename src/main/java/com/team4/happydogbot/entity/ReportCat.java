package com.team4.happydogbot.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * A class that describes user reports on the state of the animal
 *
 * @param 'examination' field for examination status (performed by a volunteer) of the report (default is null)
 * @see AdopterCat
 * @see Cat
 * @see ReportCat
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "report_cat")
public class ReportCat {
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
    @Enumerated(EnumType.STRING)
    private ExaminationStatus examination = ExaminationStatus.UNCHECKED;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    @JsonBackReference
    private AdopterCat adopterCat;

    public ReportCat(Long id, LocalDate reportDate, String fileId, String caption, ExaminationStatus examination) {
        this.id = id;
        this.reportDate = reportDate;
        this.fileId = fileId;
        this.caption = caption;
        this.examination = examination;
    }
}
