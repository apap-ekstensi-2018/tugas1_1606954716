$(document).ready( function () {
    console.log($("#univ").val())
    console.log($("#fakultas").val())
    console.log($("#prodi").val());
    var tabel = loaddata($("#univ").val(),$("#fakultas").val(),$("#prodi").val());
    $("#employeesTable tbody").on('click', 'button[id="#btnUpdate"]', function(){
        var data = tabel.row($(this).parents('tr')).data();
        window.open("/student/update/"+data["npm"]);
    })

    $("#employeesTable tbody").on('click', 'button[id="#btnDelete"]', function(){
        var data = tabel.row($(this).parents('tr')).data();
        var bool = confirm("Apakah anda yakin?")
        if(bool){
            $.ajax({
                url: "/student/delete/"+data["npm"],
                success: function(e){
                    tabel.ajax.reload();
                }
            });
        }
    })
});

function loaddata(univ, fakultas, prodi){
    var iDisplayIndex = 0,nRow;
    var tabel = $("#employeesTable").DataTable({
        "serverSide": true,
        "columnDefs": [ {
            "searchable": false,
            "orderable": false,
            "targets": 0
        } ],
        "ajax": {
            "url": "/mahasiswa/viewData",
            "data": {
                "univ" :  univ,
                "fakultas" : fakultas,
                "prodi" : prodi
            }
        },
        "sAjaxDataProp": "",
        "columns": [
            {
                render: function (data, type, row, meta) {
                    return meta.row + meta.settings._iDisplayStart + 1;
                }
            },
            { data: "npm" },
            { data: "nama_mahasiswa" },
            { data: "nama_program_studi" },
            { data: "tahun_masuk" },
            { data: "jalur_masuk" },
            {
                defaultContent: "<button class='btn btn-default btn-xs' id='#btnViewDetail'>Lihat Detail</button>"
            }
        ]
    });
    return tabel;
}