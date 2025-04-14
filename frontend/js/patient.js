$(document).ready(function () {
    loadHospitalIds();
    loadPatient();

});


$("#saveAppointment").click(function () {
    let patientData = {
        fullName: $("#patientName").val(),
        gender: $("#Gender").val(),
        age: $("#age").val(),
        contactNum: $("#contact").val(),
        address: $("#address").val(),
        doctorID: $("#DoctorDropdown").val() // Capture selected doctor ID
    };

    $.ajax({
        url: "http://localhost:8081/api/v1/patient/save",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(patientData),
        success: function (response) {
            alert("Appointment placed successfully!");
            console.log(response);
        },
        error: function (error) {
            let errorMessage = "An error occurred. Please try again.";

            // Check if the error response contains validation errors
            if (error.responseJSON && error.responseJSON.data) {
                // Extract the first error message from the data object
                const errorData = error.responseJSON.data;
                const firstErrorKey = Object.keys(errorData)[0]; // Get the first key (e.g., "hospitalName")
                errorMessage = errorData[firstErrorKey]; // Get the error message for that key
            }

            // Display the error message using SweetAlert
            alert(errorMessage)
        }
    });
});



function loadHospitalIds() {
    $.ajax({
        url: "http://localhost:8081/api/v1/patient/getAllIds",
        method: "GET",
        dataType: "json",
        success: function (response) {
            if (!response.data || response.data.length === 0) {
                alert("No doctor IDs found.");
                return;
            } else {
                alert("doctor id found.");
            }

            // Log the response to check the data structure
            let dropdown = $("#DoctorDropdown");
            dropdown.empty();
            dropdown.append(`<option value="">Select a doctor ID</option>`);

            // Iterate over each object in the response.data array
            response.data.forEach(doc => {
                let doctorID = doc.doctorID;  // Access hospital ID
                let docName = doc.name;  // Access hospital Name

                // Create the dropdown option with both ID and Name
                let option = `<option value="${doctorID}">${doctorID}</option>`;
                dropdown.append(option);
            });
        },
        error: function (xhr, status, error) {
            console.error("Error fetching hospitals:", xhr, status, error);
            alert("Failed to load hospital IDs.");
        }
    });
}


function loadPatient() {
    $.ajax({
        url: "http://localhost:8081/api/v1/patient/getAll",
        method: "GET",
        dataType: "json",
        success: function (response) {
            if (response.data) {
                let patientTableBody = $("#patientTableBody");

                patientTableBody.empty(); // Clear existing table data



                response.data.forEach(patient => {


                    let doc = patient.doctorID ? patient.doctorID.doctorID : "N/a";

                    let row = `<tr>
                        <td>${patient.patientID}</td>
                        <td>${patient.fullName}</td>
                        <td>${patient.address}</td>
                        <td>${patient.contactNum}</td>
                        <td>${patient.age}</td>
                        <td>${patient.gender}</td>
                        <td>${patient.status}</td>
                         <td>${doc}</td>
                         <td>
                         <button type="button" 
        class="btn bg-success bg-gradient mt-1 me-2 text-light bg-opacity-70" 
        onclick="updateStatusPatient('${patient.patientID}')"
        style="display: ${patient.status === 'Rejected' || patient.status === 'Approved' ? 'none' : 'inline-block'};">
        Approve
    </button>
    
    <!-- Show the "Reject" button only if the status is neither "Rejected" nor "Approved" -->
    <button type="button" 
        class="btn bg-danger bg-gradient mt-1 me-2 text-light bg-opacity-70" 
        onclick="RejectPatient('${patient.patientID}')"
        style="display: ${patient.status === 'Rejected' || patient.status === 'Approved' ? 'none' : 'inline-block'};">
        Reject
    </button>
                        </td>
                        
                    </tr>`;
                    patientTableBody.append(row);
                });



                // Attach event handlers for edit/delete
            }
        },
        error: function (xhr, status, error) {
            console.error("Error loading doctors:", xhr, status, error);
            alert("Failed to load patient data.");
        }
    });
}


function updateStatusPatient(patientID){

    $.ajax({
        url:`http://localhost:8081/api/v1/patient/updateStatus/${patientID}`,
        method:"PUT",
        dataType:"json",
        success:function (res){
            console.log(res.message)
            loadPatient();

        },
        error: function (error) {
            let errorMessage = "An error occurred. Please try again.";

            // Check if the error response contains validation errors
            if (error.responseJSON && error.responseJSON.data) {
                // Extract the first error message from the data object
                const errorData = error.responseJSON.data;
                const firstErrorKey = Object.keys(errorData)[0]; // Get the first key (e.g., "hospitalName")
                errorMessage = errorData[firstErrorKey]; // Get the error message for that key
            }

            // Display the error message using SweetAlert
            alert(errorMessage)
        }
    });

}


function RejectPatient(patientID){

    $.ajax({
        url:`http://localhost:8081/api/v1/patient/updateStatusReject/${patientID}`,
        method:"PUT",
        dataType:"json",
        success:function (res){
            console.log(res.message)
            loadPatient();

        },
        error:function (error){
            console.log(error.message)
        }
    });

}