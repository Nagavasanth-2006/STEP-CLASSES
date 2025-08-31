import java.util.*;

class Patient {
    String patientId, patientName, gender, contactInfo;
    int age;
    List<String> medicalHistory = new ArrayList<>();
    List<String> currentTreatments = new ArrayList<>();

    static int totalPatients = 0;

    Patient(String patientId, String patientName, int age, String gender, String contactInfo) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.age = age;
        this.gender = gender;
        this.contactInfo = contactInfo;
        totalPatients++;
    }

    void updateTreatment(String treatment) {
        currentTreatments.add(treatment);
    }

    void dischargePatient() {
        medicalHistory.addAll(currentTreatments);
        currentTreatments.clear();
    }
}

class Doctor {
    String doctorId, doctorName, specialization;
    List<String> availableSlots = new ArrayList<>();
    int patientsHandled;
    double consultationFee;

    Doctor(String doctorId, String doctorName, String specialization, double consultationFee, String[] slots) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.consultationFee = consultationFee;
        this.availableSlots.addAll(Arrays.asList(slots));
    }

    boolean isAvailable(String time) {
        return availableSlots.contains(time);
    }

    void bookSlot(String time) {
        availableSlots.remove(time);
        patientsHandled++;
    }
}

class Appointment {
    String appointmentId, appointmentDate, appointmentTime, status, type;
    Patient patient;
    Doctor doctor;

    static int totalAppointments = 0;
    static String hospitalName = "CityCare Hospital";
    static double totalRevenue = 0;

    Appointment(String appointmentId, Patient patient, Doctor doctor, String date, String time, String type) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = date;
        this.appointmentTime = time;
        this.type = type;
        this.status = "Scheduled";
        totalAppointments++;
    }

    void cancelAppointment() {
        this.status = "Cancelled";
    }

    double generateBill() {
        double fee = doctor.consultationFee;
        if (type.equalsIgnoreCase("Emergency")) {
            fee *= 2;
        } else if (type.equalsIgnoreCase("Follow-up")) {
            fee *= 0.5;
        }
        totalRevenue += fee;
        return fee;
    }
}

class HospitalManager {
    List<Patient> patients = new ArrayList<>();
    List<Doctor> doctors = new ArrayList<>();
    List<Appointment> appointments = new ArrayList<>();

    void addPatient(Patient p) {
        patients.add(p);
    }

    void addDoctor(Doctor d) {
        doctors.add(d);
    }

    Appointment scheduleAppointment(String appointmentId, String patientId, String doctorId, String date, String time, String type) {
        Patient patient = findPatient(patientId);
        Doctor doctor = findDoctor(doctorId);

        if (patient != null && doctor != null && doctor.isAvailable(time)) {
            doctor.bookSlot(time);
            Appointment a = new Appointment(appointmentId, patient, doctor, date, time, type);
            appointments.add(a);
            return a;
        }
        return null;
    }

    Patient findPatient(String id) {
        for (Patient p : patients) {
            if (p.patientId.equals(id)) return p;
        }
        return null;
    }

    Doctor findDoctor(String id) {
        for (Doctor d : doctors) {
            if (d.doctorId.equals(id)) return d;
        }
        return null;
    }

    static void generateHospitalReport() {
        System.out.println("Hospital Name: " + Appointment.hospitalName);
        System.out.println("Total Patients: " + Patient.totalPatients);
        System.out.println("Total Appointments: " + Appointment.totalAppointments);
        System.out.println("Total Revenue: $" + Appointment.totalRevenue);
    }

    void getDoctorUtilization() {
        for (Doctor d : doctors) {
            System.out.println(d.doctorName + " handled " + d.patientsHandled + " patients.");
        }
    }

    void getPatientStatistics() {
        for (Patient p : patients) {
            System.out.println(p.patientName + ": " + p.medicalHistory.size() + " past treatments, " +
                    p.currentTreatments.size() + " ongoing treatments.");
        }
    }
}

public class HospitalSystem {
    public static void main(String[] args) {
        HospitalManager hm = new HospitalManager();

        Doctor d1 = new Doctor("D001", "Dr. Smith", "Cardiology", 500, new String[]{"10:00", "11:00", "12:00"});
        Doctor d2 = new Doctor("D002", "Dr. Jane", "Neurology", 600, new String[]{"13:00", "14:00"});

        hm.addDoctor(d1);
        hm.addDoctor(d2);

        Patient p1 = new Patient("P001", "Alice", 30, "Female", "1234567890");
        Patient p2 = new Patient("P002", "Bob", 45, "Male", "9876543210");

        hm.addPatient(p1);
        hm.addPatient(p2);

        Appointment a1 = hm.scheduleAppointment("A001", "P001", "D001", "2025-09-01", "10:00", "Consultation");
        Appointment a2 = hm.scheduleAppointment("A002", "P002", "D002", "2025-09-01", "13:00", "Emergency");

        if (a1 != null) {
            System.out.println("Appointment A001 Bill: $" + a1.generateBill());
        }
        if (a2 != null) {
            System.out.println("Appointment A002 Bill: $" + a2.generateBill());
        }

        p1.updateTreatment("Blood Pressure Monitoring");
        p2.updateTreatment("MRI Scan");

        p1.dischargePatient();
        p2.dischargePatient();

        System.out.println();
        HospitalManager.generateHospitalReport();
        System.out.println();
        hm.getDoctorUtilization();
        System.out.println();
        hm.getPatientStatistics();
    }
}
