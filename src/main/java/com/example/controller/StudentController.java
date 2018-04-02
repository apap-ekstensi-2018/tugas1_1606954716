package com.example.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import com.example.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.example.service.StudentService;

import javax.swing.text.html.Option;

import static org.thymeleaf.util.StringUtils.concat;

@Controller
public class StudentController
{
    String npm_generate, kode_jalur_masuk, Nomor;
    @Autowired
    StudentService studentDAO;

    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }


    @RequestMapping("/mahasiswa/tambah")
    public String add (Model model)
    {
        List<StudyProgramModel> studyProgram = studentDAO.selectAllStudyProgram ();
        model.addAttribute("studyProgram", studyProgram);
        return "form-add";
    }


    @RequestMapping(value = "/mahasiswa/tambah/submit", method = RequestMethod.POST)
    public String addSubmit (
            @RequestParam(value="nama", required = false) String nama,
            @RequestParam(value="tempat_lahir", required = false) String tempat_lahir,
            @RequestParam(value="tanggal_lahir", required = false) String tanggal_lahir,
            @RequestParam(value="jenis_kelamin", required = false) Integer jenis_kelamin,
            @RequestParam(value="agama", required = false) String agama,
            @RequestParam(value="golongan_darah", required = false) String golongan_darah,
            @RequestParam(value="tahun_masuk", required = false) String tahun_masuk,
            @RequestParam(value="jalur_masuk", required = false) String jalur_masuk,
            @RequestParam(value="id_prodi", required = false) Integer id_prodi,
            Model model)
    {
        StudyProgramModel studyProgram = studentDAO.selectStudyProgram(id_prodi);
        FacultyModel faculty = studentDAO.selectFaculty(studyProgram.getId_fakultas());
        UniversityModel university = studentDAO.selectUniversity(faculty.getId_univ());

        if(jalur_masuk.equals("Undangan Olimpiade")){
            kode_jalur_masuk = "53";
        } else if(jalur_masuk.equals("Undangan Reguler/SNMPTN")){
            kode_jalur_masuk = "54";
        } else if(jalur_masuk.equals("Undangan Paralel/PPKB")){
            kode_jalur_masuk = "55";
        } else if(jalur_masuk.equals("Ujian Tulis Bersama/SBMPTN")){
            kode_jalur_masuk = "57";
        } else if(jalur_masuk.equals("Ujian Tulis Mandiri")){
            kode_jalur_masuk = "62";
        }

        Integer urutan_npm = studentDAO.getNpm(concat("%",tahun_masuk.substring(2,4),university.getKode_univ(),studyProgram.getKode_prodi(),kode_jalur_masuk,"%"));
        if(urutan_npm != null){
            if (urutan_npm < 10){
                urutan_npm += 1;
                Nomor = concat("00",String.valueOf(urutan_npm));
            } else if(urutan_npm < 100){
                urutan_npm += 1;
                Nomor = concat("0", String.valueOf(urutan_npm));
            } else {
                urutan_npm += 1;
                Nomor = String.valueOf(urutan_npm);
            }
        }else{
            Nomor = "001";
        }
        String npm_generate = concat(tahun_masuk.substring(2,4),university.getKode_univ(),studyProgram.getKode_prodi(),kode_jalur_masuk, Nomor);

        if(npm_generate.isEmpty()){
            return "not-found";
        }else{
            StudentModel student = new StudentModel (npm_generate, nama, tempat_lahir,
                    tanggal_lahir, jenis_kelamin, agama, golongan_darah,"aktif",
                    tahun_masuk,jalur_masuk,id_prodi);
            studentDAO.addStudent (student);
            model.addAttribute("selectStudent", npm_generate);
            return "success-add";
        }
    }

    @RequestMapping("/mahasiswa")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
        StudentModel student = studentDAO.selectStudent(npm);
        StudyProgramModel studyProgram = studentDAO.selectStudyProgram (student.getId_prodi());
        FacultyModel faculty = studentDAO.selectFaculty(studyProgram.getId_fakultas());
        UniversityModel university = studentDAO.selectUniversity(faculty.getId_univ());

        if (student != null) {
            model.addAttribute("studyProgram", studyProgram);
            model.addAttribute ("student", student);
            model.addAttribute ("faculty", faculty);
            model.addAttribute ("university", university);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }



    @RequestMapping("/mahasiswa/cari")
    public String cariMahasiswa(Model model,
                                @RequestParam(value = "univ", required = false) Optional<String> univ,
                                @RequestParam(value = "fakultas", required = false) Optional<String> fakultas,
                                @RequestParam(value = "prodi", required = false) Optional<String> prodi)
    {

        if (univ.isPresent() && fakultas.isPresent() && prodi.isPresent()){
            List<StudentDataTableModel> students = studentDAO.selectStudentDataTable (Integer.parseInt(univ.get()), Integer.parseInt(fakultas.get()), Integer.parseInt(prodi.get()));
            model.addAttribute("univ", univ.get());
            model.addAttribute("fakultas", fakultas.get());
            model.addAttribute("prodi", prodi.get());
            model.addAttribute("students", students);
            return "viewall";
        }else{
            return "cari-mahasiswa";
        }
    }

    @RequestMapping("/mahasiswa/ubah/{npm}")
    public String viewPath (Model model,
                            @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            List<StudyProgramModel> studyProgram = studentDAO.selectAllStudyProgram ();
            model.addAttribute("studyProgram", studyProgram);
            model.addAttribute ("student", student);
            return "form-update";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }

    @RequestMapping(value = "/mahasiswa/ubah/{npm}", method = RequestMethod.POST)
    public String updateSubmit (@ModelAttribute("student") StudentModel student, ModelMap model)
    {
        if(student.getNpm() == null || student.getNpm() == "") {
            return "error";
        }

        StudyProgramModel studyProgram = studentDAO.selectStudyProgram(student.getId_prodi());
        FacultyModel faculty = studentDAO.selectFaculty(studyProgram.getId_fakultas());
        UniversityModel university = studentDAO.selectUniversity(faculty.getId_univ());

        StudentModel studentValid = studentDAO.selectStudent(student.getNpm());
        if((!student.getJalur_masuk().equals(studentValid.getJalur_masuk()))  ||
            (!student.getTahun_masuk().equals(studentValid.getTahun_masuk())) ||
            (student.getId_prodi() != studentValid.getId_prodi()))
        {
            if(student.getJalur_masuk().equals("Undangan Olimpiade")){
                kode_jalur_masuk = "53";
            } else if(student.getJalur_masuk().equals("Undangan Reguler/SNMPTN")){
                kode_jalur_masuk = "54";
            } else if(student.getJalur_masuk().equals("Undangan Paralel/PPKB")){
                kode_jalur_masuk = "55";
            } else if(student.getJalur_masuk().equals("Ujian Tulis Bersama/SBMPTN")){
                kode_jalur_masuk = "57";
            } else if(student.getJalur_masuk().equals("Ujian Tulis Mandiri")){
                kode_jalur_masuk = "62";
            }

            Integer urutan_npm = studentDAO.getNpm(concat("%",student.getTahun_masuk().
                    substring(2,4),
                    university.getKode_univ(),
                    studyProgram.getKode_prodi(),
                    kode_jalur_masuk,"%"));
            if(urutan_npm != null){
                if (urutan_npm < 10){
                    urutan_npm += 1;
                    Nomor = concat("00",String.valueOf(urutan_npm));
                } else if(urutan_npm < 100){
                    urutan_npm += 1;
                    Nomor = concat("0", String.valueOf(urutan_npm));
                } else {
                    urutan_npm += 1;
                    Nomor = String.valueOf(urutan_npm);
                }
            }else{
                Nomor = "001";
            }
            npm_generate = concat(student.getTahun_masuk().substring(2,4),university.getKode_univ(),studyProgram.getKode_prodi(),kode_jalur_masuk, Nomor);

            studentDAO.updateStudent(student.getNpm(), npm_generate,
                    student.getNama(), student.getTempat_lahir(),
                    student.getTanggal_lahir(), student.getJenis_kelamin(),
                    student.getAgama(), student.getGolongan_darah(),
                    student.getStatus(), student.getTahun_masuk(),
                    student.getJalur_masuk(), student.getId_prodi());
            model.addAttribute("npm",npm_generate);
            return "success-update";

        } else {

            studentDAO.updateStudent(student.getNpm(), student.getNpm(),
                    student.getNama(), student.getTempat_lahir(),
                    student.getTanggal_lahir(), student.getJenis_kelamin(),
                    student.getAgama(), student.getGolongan_darah(),
                    student.getStatus(), student.getTahun_masuk(),
                    student.getJalur_masuk(), student.getId_prodi());
            model.addAttribute("npm",student.getNpm());
            return "success-update";
        }


    }

    @RequestMapping("/mahasiswa/usia")
    public String usia(Model model,@RequestParam(value="thn", required = false) Optional<String>  thn,
                       @RequestParam(value="prodi", required = false) Optional<String>  prodi)
    {
        if(thn.isPresent() && prodi.isPresent()){
            List<StudentOldestAndYoungest> students = studentDAO.selectOldestAndYoungestStudent(thn.get(),prodi.get());
            model.addAttribute("students", students);
            return "usia";
        }else{
            return "form-usia";
        }
    }

    @RequestMapping("/kelulusan")
    public String kelulusanPage (Model model,
                                 @RequestParam(value="thn", required = false) Optional<String>  thn,
                                 @RequestParam(value="prodi", required = false) Optional<String>  prodi)
    {
        if(thn.isPresent() && prodi.isPresent()){
            int jumlah_mhs = studentDAO.selectStudentByYear(thn.get(), Integer.parseInt(prodi.get()));
            int jumlah_mhs_lulus = studentDAO.selectGraduateStudent(thn.get(), Integer.parseInt(prodi.get()));
            double persen = ((double) jumlah_mhs_lulus / (double) jumlah_mhs) * 100;
            String persentase = new DecimalFormat("##.##").format(persen);
            StudyProgramModel program_studi = studentDAO.selectStudyProgram(Integer.parseInt(prodi.get()));
            FacultyModel fakultas = studentDAO.selectFaculty(program_studi.getId_fakultas());
            UniversityModel university = studentDAO.selectUniversity(fakultas.getId_univ());
            model.addAttribute("jumlah_mhs", jumlah_mhs);
            model.addAttribute("jumlah_mhs_lulus", jumlah_mhs_lulus);
            model.addAttribute("presentase", persentase);
            model.addAttribute("tahun", thn.get());
            model.addAttribute("prodi", program_studi.getNama_prodi());
            model.addAttribute("fakultas", fakultas.getNama_fakultas());
            model.addAttribute("universitas", university.getNama_univ());

            return "kelulusanhasil";
        }else{
            List<StudyProgramModel> studyProgram = studentDAO.selectAllStudyProgram ();
            model.addAttribute("studyProgram", studyProgram);
            return "kelulusan";
        }
    }

    @RequestMapping(value="/mahasiswa/lulus/{npm}")
    public String updateLulus(Model model,  @PathVariable(value = "npm") String npm){
        studentDAO.updateStatus("Lulus",npm);
        model.addAttribute("selectStudent", npm);
        return "success-lulus";
    }

    @RequestMapping(value="/mahasiswa/nonaktif/{npm}")
    public String updateNonaktif(Model model,  @PathVariable(value = "npm") String npm){
        studentDAO.updateStatus("Drop Out",npm);
        model.addAttribute("selectStudent", npm);
        return "success-nonaktifkan";
    }

    @RequestMapping(value="/mahasiswa/select/universitas", method=RequestMethod.GET)
    public ResponseEntity<List<UniversityModel>> selectUniversitas(Model model)
    {
        List<UniversityModel> university = studentDAO.selectAllUniversities ();

        return new ResponseEntity<List<UniversityModel>>(university, HttpStatus.OK);
    }

    @RequestMapping(value="/mahasiswa/select/fakultas", method=RequestMethod.GET)
    public ResponseEntity<List<FacultyModel>> selectFaculty(Model model, @RequestParam(value="univ", required = false) String  univ)
    {
        List<FacultyModel> faculty = studentDAO.selectFacultyByUniversity(Integer.parseInt(univ));

        return new ResponseEntity<List<FacultyModel>>(faculty, HttpStatus.OK);
    }

    @RequestMapping(value="/mahasiswa/select/studiprogram", method=RequestMethod.GET)
    public ResponseEntity<List<StudyProgramModel>> selectStudyProgram(Model model, @RequestParam(value="fakultas", required = false) Optional<String>  fakultas)
    {
        if(fakultas.isPresent())
        {
            List<StudyProgramModel> studyProgram = studentDAO.selectStudyProgramByFaculty (Integer.valueOf(fakultas.get()));
            return new ResponseEntity<List<StudyProgramModel>>(studyProgram, HttpStatus.OK);
        } else {
            List<StudyProgramModel> studyProgram = studentDAO.selectAllStudyProgram();
            return new ResponseEntity<List<StudyProgramModel>>(studyProgram, HttpStatus.OK);
        }
    }
}
