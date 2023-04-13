package com.example.tp2jpa;

import com.example.tp2jpa.entities.Patient;
import com.example.tp2jpa.repo.PatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class Tp2JpaApplication implements CommandLineRunner {

	@Autowired
	private PatientRepo patientRepo;
	public static void main(String[] args) {
		SpringApplication.run(Tp2JpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		for (int i = 0; i < 100; i++) {
			patientRepo.save(new Patient(null,generateRandomString(),new Date(), Math.random()>0.5?true:false, (int) (Math.random()*100) ));

		}

		patientRepo.save(new Patient(null,"hassan",new Date(),false,50));
		patientRepo.save(new Patient(null,"hamza",new Date(),true,30));
		patientRepo.save(new Patient(null,"moad",new Date(),false,100));

		Page<Patient> patients = patientRepo.findAll(PageRequest.of(5,5));
		System.out.println("total element : "+patients.getTotalElements());
		System.out.println("total page : "+patients.getTotalPages());
		System.out.println("num page : "+patients.getNumber());
		List<Patient> content = patients.getContent();


		Page<Patient> byMalade = patientRepo.findByMalade(true,PageRequest.of(0,5));
		List<Patient> patients1 = patientRepo.chercherPatients("%h%",40);
		patients1.forEach(p->{
			System.out.println("======================================");
			System.out.println(p.getId());
			System.out.println(p.getNom());
			System.out.println(p.getDateNaissance());
			System.out.println(p.isMalade());
			System.out.println(p.getScore());
		});

		System.out.println("======================================");
		Patient patient = patientRepo.findById(1L).orElse(null);
		if (patient !=null){
			System.out.println(patient.getNom());
			System.out.println(patient.isMalade());
		}

		patient.setScore(870);
		patientRepo.save(patient);
		patientRepo.deleteById(1L);

	}
	public static String generateRandomString() {
		Random random = new Random();
		int length = random.nextInt(10 - 4 + 1) + 10;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			char c = (char)(random.nextInt(26) + 'a');
			sb.append(c);
		}
		return sb.toString();
	}
}
