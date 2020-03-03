var columns = [
    {
        field: "geneid",
        title: "Gene_ID",
        align:"center",
        halign:"center",
        valign:"middle",
        formatter: function (value, row) {
            return "<p><a href='/CGDB/genome/moreInfo?id=" + row.id + "' target='_blank'>" + row.geneid + "</a></p>";
        }
    }, {
        field: "chr",
        title: "Chr",
        align:"center",
        halign:"center",
        valign:"middle"
    }, {
        field: "start",
        title: "Start",
        align:"center",
        halign:"center",
        valign:"middle"
    }, {
        field: "end",
        title: "End",
        align:"center",
        halign:"center",
        valign:"middle"
    }, {
        field: "strand",
        title: "Strand",
        align:"center",
        halign:"center",
        valign:"middle"
    }
];

function checkBox() {

    var array = ["Chr", "Strand", "Start", "End", "COG Class", "COG Class Annotation", "GO", "KEGG",
        "KOG Class", "KOG Class Annotation", "Pfam", "Swissprot", "TrEMBL", "nr"];
    var values = ["chr", "strand", "start", "end", "cogClass", "cogAnno", "go", "kegg", "kogClass",
        "kogAnno", "pfam", "swiss", "trembl", "nr"];
    var tHtml = "";
    $.each(array, function (i, v) {
        tHtml += "<th data-field='" + values[i] + "' data-sortable='true'>" + v + "</th>"
    });

    $("#marker").after(tHtml);

    var html = "";
    $.each(array, function (n, value) {
            html += "<label style='margin-right: 15px'>" +
                "<input type='checkbox' checked='checked' value='" + values[n] + "' onclick=\"setColumns('" + values[n] + "')\">" + value +
                "</label>"
        }
    );
    $("#checkbox").append(html);
}

function hidden() {
    var hiddenArray = ["cogAnno", "kogAnno", "pfam", "trembl", "nr"];

    $.each(hiddenArray, function (n, value) {
            $('#table').bootstrapTable('hideColumn', value);
            $("input:checkbox[value=" + value + "]").attr("checked", false)
        }
    );
}

function setColumns(value) {
    var element = $("input:checkbox[value=" + value + "]");
    if (element.is(":checked")) {
        $('#table').bootstrapTable('showColumn', value);
    } else {
        $('#table').bootstrapTable('hideColumn', value);
    }
}

/**
 * 
 * Browse jQuery 代码
 * 
 */
function Browse(species) {
    console.log(species);

    $("#table").bootstrapTable('destroy');
    $('#table').bootstrapTable({
        method: 'post',
        url: "/CGDB/genome/getAllBySpecies?species=" + species,
        sidePagination: "server",
        pageNumber: 1,
        pagination: true,
        pageList: [10, 25],
        contentType: "application/x-www-form-urlencoded",
        columns: columns
    });

}

function formValidation() {
    $('#geneIdForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            geneId: {
                validators: {
                    notEmpty: {
                        message: 'Gene Id is required！'
                    }
                }
            }
        }
    });
    $('#regionForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            chr: {
                validators: {
                    notEmpty: {
                        message: 'Chromosome is required！'
                    }
                }
            },
            start: {
                validators: {
                    notEmpty: {
                        message: 'Start is required！'
                    },
                    integer: {
                        message: 'Start must be integer！'
                    }

                }
            },
            end: {
                validators: {
                    notEmpty: {
                        message: 'End is required！'
                    },
                    integer: {
                        message: 'End must be integer！'
                    }

                }
            }
        }
    });

}

function geneIdSearch() {
    var form = $("#geneIdForm")
    var fv = form.data("formValidation");
    fv.validate();
    if (fv.isValid()) {
        var element = "<div id='content'><span id='info'>Query...</span>&nbsp;<img class='runningImage' src='/assets/images/running2.gif' style='width: 30px;height: 20px;'></div>"
        var index = layer.alert(element, {
            skin: 'layui-layer-molv'
            , closeBtn: 0,
            title:"Info",
            btn: []
        });

        $("#search").attr("disabled", true).html("Search...");
        $.ajax({
            url: "/CGDB/genome/searchById",
            type: "post",
            data: $("#geneIdForm").serialize(),
            success: function (data) {
                $('#table').bootstrapTable("load", data);
                $("#search").attr("disabled", false).html("Search").blur();
                layer.close(index);
                $("#result").show()
            }
        });
    }
}

function regionSearch() {
    var form = $("#regionForm")
    var fv = form.data("formValidation");
    fv.validate();
    if (fv.isValid()) {
        var element = "<div id='content'><span id='info'>Query...</span>&nbsp;<img class='runningImage' src='/assets/images/running2.gif' style='width: 30px;height: 20px;'></div>"
        var index = layer.alert(element, {
            skin: 'layui-layer-molv'
            , closeBtn: 0,
            title:"Info",
            btn: []
        });

        $("#search").attr("disabled", true).html("Search...");
        $.ajax({
            url: "/CGDB/genome/searchByRegion",
            type: "post",
            data: $("#regionForm").serialize(),
            success: function (data) {
                $('#table').bootstrapTable("load", data);
                $("#search").attr("disabled", false).html("Search").blur();
                layer.close(index);
                $("#result").show()
            }
        });
    }
}

$('#egChr').click(function () {
    var eg = $(this).text().trim();
    $('#chr').val(eg);
    $("#regionForm").formValidation("revalidateField", "chr")
});

$('#egStart').click(function () {
    var eg = $(this).text().trim();
    $('#start').val(eg);
    $("#regionForm").formValidation("revalidateField", "start")
});

$('#egEnd').click(function () {
    var eg = $(this).text().trim();
    $('#end').val(eg);
    $("#regionForm").formValidation("revalidateField", "end")
});


/**
 *
 * 根据条件搜索 jQuery 代码
 * 
 */
function SearchTable() {
    egGene();

    hideResult();


    $.ajax({
        url: "/CGDB/utils/getAllChr",
        type: "post",
        data:$("#regionForm").serialize(),
        success: function (data) {
            $("#chr").select2({data: data})
        }
    });



    $("#table").bootstrapTable({columns:columns});



    formValidation()
}
