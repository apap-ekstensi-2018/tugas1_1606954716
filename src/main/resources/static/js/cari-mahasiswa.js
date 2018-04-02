$(document).ready(function(){
    var selectUniv, $selectUniv, selectFaculty, $selectFaculty, selectStudyProgram, $selectStudyProgram;
    $("#fakultas").prop('disabled', true);
    $("#prodi").prop('disabled', true);
    $selectUniv = $("#univ").selectize({
        valueField: 'id',
        labelField: 'nama_univ',
        searchField: 'nama_univ',
        create: false,
        preload: true,
        options: [],
        load: function(query, callback){
            $.ajax({
                url: "/mahasiswa/select/universitas",
                type: "GET",
                error: function(){
                    callback();
                },
                success: function(res){
                    callback(res);
                }
            })
        },
        onChange: function(value){
            if(!value.length) return;
            selectFaculty.disable();
            selectStudyProgram.disable();
            selectFaculty.clearOptions();
            selectStudyProgram.clearOptions();
            selectFaculty.load(
                function(callback){
                    $.ajax({
                        url: "/mahasiswa/select/fakultas?univ=" + value,
                        type: "GET",
                        error: function(){
                            callback();
                        },
                        success: function(res){
                            selectFaculty.enable();
                            callback(res);

                        }
                    })
                }
            )
        }
    });

    $selectFaculty = $("#fakultas").selectize({
            valueField: 'id',
            labelField: 'nama_fakultas',
            searchField: 'nama_fakultas',
            create: false,
            preload: true,
            options: [],
            load: function(query, callback){
                $.ajax({
                    url: "/mahasiswa/select/fakultas",
                    type: "GET",
                    error: function(){
                        callback();
                    },
                    success: function(res){
                        callback(res);

                    }
                })
            },
            onChange: function(value){
                 if(!value.length) return;
                 selectStudyProgram.disable();
                 selectStudyProgram.clearOptions();
                 selectStudyProgram.load(
                     function(callback){
                         $.ajax({
                             url: "/mahasiswa/select/studiprogram?fakultas=" + value,
                             type: "GET",
                             error: function(){
                                 callback();
                             },
                             success: function(res){
                                 selectStudyProgram.enable();
                                 callback(res);

                             }
                         })
                     }
                 )
            }
        });

    $selectStudyProgram = $("#prodi").selectize({
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

                    }
                })
            }
    });
    selectUniv = $selectUniv[0].selectize;
    selectFaculty = $selectFaculty[0].selectize;
    selectStudyProgram = $selectStudyProgram[0].selectize;
});
