$(document).ready(function(){
    var jenis_kelamin, $jenis_kelamin, agama, $agama, golongan_darah, $golongan_darah, jalur_masuk, $jalur_masuk, prodi, $prodi;

    $jenis_kelamin = $("#jenis_kelamin").selectize({
        options: [
            {id: 1, text: 'Laki - Laki'},
            {id: 0, text: 'Perempuan'}
        ],
        labelField: 'text',
        valueField: 'id',
        onChange: function(e){
            $("#jenkel_parent").find("span").remove();
        }
    });

    $agama = $("#agama").selectize({
        options: [
            {id: 'Islam', text: 'Islam'},
            {id: 'Protestan', text: 'Protestan'},
            {id: 'Katolik', text: 'Katolik'},
            {id: 'Budha', text: 'Budha'},
            {id: 'Hindu', text: 'Hindu'},
            {id: 'Konghucu', text: 'Konghucu'}
        ],
        labelField: 'text',
        valueField: 'id',
        onChange: function(e){
            $("#agama_parent").find("span").remove();
        }
    });

    $golongan_darah = $("#golongan_darah").selectize({
        options: [
            {id: 'AB-', text: 'AB-'},
            {id: 'AB+', text: 'AB+'},
            {id: 'A-', text: 'A-'},
            {id: 'A+', text: 'A+'},
            {id: 'B-', text: 'B-'},
            {id: 'B+', text: 'B+'},
            {id: 'C-', text: 'C-'},
            {id: 'C+', text: 'C+'},
            {id: 'O-', text: 'O-'},
            {id: 'O+', text: 'O+'}
        ],
        labelField: 'text',
        valueField: 'id',
        onChange: function(e){
            $("#goldar_parent").find("span").remove();
        }
    });

    $jalur_masuk = $("#jalur_masuk").selectize({
        options: [
            {id: 'Undangan Olimpiade', text: 'Undangan Olimpiade'},
            {id: 'Undangan Reguler/SNMPTN', text: 'Undangan Reguler/SNMPTN'},
            {id: 'Undangan Paralel/PPKB', text: 'Undangan Paralel/PPKB'},
            {id: 'Ujian Tulis Bersama/SBMPTN', text: 'Ujian Tulis Bersama/SBMPTN'},
            {id: 'Ujian Tulis Mandiri', text: 'Ujian Tulis Mandiri'}
        ],
        labelField: 'text',
        valueField: 'id',
        onChange: function(e){
            $("#jamas_parent").find("span").remove();
        }
    });

    jenis_kelamin = $jenis_kelamin[0].selectize;
    agama = $agama[0].selectize;
    golongan_darah = $golongan_darah[0].selectize;
    jalur_masuk = $jalur_masuk[0].selectize;
    load_dropdown_prodi();

    $("#tahun_masuk").datepicker({
        format: "yyyy",
        minViewMode: 2,
        autoclose: true
    });

    $("#tanggal_lahir").datepicker({
        format: "yyyy-mm-dd",
        autoclose: true
    });

    $("#tahun_masuk").on("change", function(){
        $("#thnmsk_parent").find("span").remove();
    });

    $("#tanggal_lahir").on("change", function(){
        $("#tgllahir_parent ").find("span").remove();
    });

    jenis_kelamin.setValue($("#hidden_jenis_kelamin").val());
    agama.setValue($("#hidden_agama").val());
    golongan_darah.setValue($("#hidden_golongan_darah").val());
    jalur_masuk.setValue($("#hidden_jalur_masuk").val());
    console.log($("#hidden_id_prodi").val());

    $("#formAdd").validate({
        ignore: ':hidden:not([class~=selectized]),:hidden > .selectized, .selectize-control .selectize-input input',
        rules: {
            'nama': {
                required: true
            },
            'jenis_kelamin': {
                required: true
            },
            'tempat_lahir': {
                required: true
            },
            'tanggal_lahir': {
                required: true
            },
            'agama': {
                required: true
            },
            'golongan_darah': {
                required: true
            },
            'tahun_masuk': {
                required: true
            },
            'jalur_masuk': {
                required: true
            },
            'id_prodi': {
                required: true
            }
        },
        highlight: function(element){
            //$(element).closest('.form-group > .col-md-4 > input').removeClass('is-valid is-invalid').addClass('is-invalid');
            $(element).closest('.help-block').remove();
        },
        unhighlight: function (element) {
            //$(element).closest('.form-group > .col-md-4 > input').removeClass('is-valid is-invalid');
            $(element).closest('.help-block').remove();
        },
        errorElement: 'span',
        errorClass: 'invalid-feedback',
        errorPlacement: function (error, element) {
            element.parents('.form-group > div').append(error);
            if ($(element).hasClass('selectized'))
            {
               error.insertAfter($(element).nextAll('.selectize-control'));
            }
            else
            {
                error.insertAfter(element);
            }
        }
    })

    $("#formUpdate").validate({
        ignore: ':hidden:not([class~=selectized]),:hidden > .selectized, .selectize-control .selectize-input input',
        rules: {
            'nama': {
                required: true
            },
            'jenis_kelamin': {
                required: true
            },
            'tempat_lahir': {
                required: true
            },
            'tanggal_lahir': {
                required: true
            },
            'agama': {
                required: true
            },
            'golongan_darah': {
                required: true
            },
            'tahun_masuk': {
                required: true
            },
            'jalur_masuk': {
                required: true
            },
            'id_prodi': {
                required: true
            }
        },
        highlight: function(element){
            //$(element).closest('.form-group > .col-md-4 > input').removeClass('is-valid is-invalid').addClass('is-invalid');
            $(element).closest('.help-block').remove();
        },
        unhighlight: function (element) {
            //$(element).closest('.form-group > .col-md-4 > input').removeClass('is-valid is-invalid');
            $(element).closest('.help-block').remove();
        },
        errorElement: 'span',
        errorClass: 'invalid-feedback',
        errorPlacement: function (error, element) {
            element.parents('.form-group > div').append(error);
            if ($(element).hasClass('selectized'))
            {
               error.insertAfter($(element).nextAll('.selectize-control'));
            }
            else
            {
                error.insertAfter(element);
            }
        }
    })

});

function load_dropdown_prodi(){
    $prodi = $("#id_prodi").selectize({
            valueField: 'id',
            labelField: 'nama_prodi',
            searchField: 'nama_prodi',
            create: false,
            preload: true,
            options: [],
            load: function(query, callback){
                $.ajax({
                    url: "/mahasiswa/select/studiprogram",
                    type: "GET",
                    error: function(){
                        callback();
                    },
                    success: function(res){
                        callback(res);
                        if($("#hidden_id_prodi").val() == null || $("#hidden_id_prodi").val() == ""){
                            $prodi[0].selectize.setValue("");
                        }else{
                            $prodi[0].selectize.setValue($("#hidden_id_prodi").val());
                        }
                        console.log($prodi[0].selectize)
                    }
                })
            },
            onChange: function(e){
                $("#prodi_parent").find("span").remove();
            }
        });
    prodi = $prodi[0].selectize;
}