$(document).ready(function () {
    loadAllHospitals();

    // Search Button Click Event
    $("#searchBtn").click(function () {
        let searchText = $("#searchHospital").val().toLowerCase();
        filterHospitals(searchText);
    });

    // Reset Button Click Event
    $("#resetBtn").click(function () {
        $("#searchHospital").val("");
        loadAllHospitals();
    });
});

// Function to Load All Hospitals
function loadAllHospitals() {
    const token = localStorage.getItem('authToken');



    $.ajax({
        url: "http://localhost:8081/api/v1/hospital/getAll",
        method: "GET",
        dataType: "json",

        success: function (response) {
            let hospitalList = $("#hospitalList");
            hospitalList.empty();

            response.data.forEach(hospital => {
                let hospitalCard = `
                    <div class="hospital-card">
                        <img src="../template/css/pngtree-hospital-logo-icon-abstract-alliance-picture-image_8313149.png" style="width: 100px" alt="Hospital Image" class="hospital-img">
                        <h3>${hospital.hospitalName}</h3>
                        <p><strong>Contact:</strong> ${hospital.contactNum}</p>
                    </div>
                `;
                hospitalList.append(hospitalCard);
            });
        },
        error: function (error) {
            console.error("Error loading hospitals:", error);
            $("#hospitalList").html("<p style='color: red;'>Failed to load hospitals. Please try again.</p>");
        }
    });
}

// Function to Filter Hospitals
function filterHospitals(searchText) {
    $(".hospital-card").each(function () {
        let hospitalName = $(this).find("h3").text().toLowerCase();
        if (hospitalName.includes(searchText)) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });
}
