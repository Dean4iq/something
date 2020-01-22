$(document).ready(function() {
    $("#callAjaxButton").click(function(e) {
        e.preventDefault();
        $("#callAjaxButton").attr("disabled", true);

        var textInputDto = {
           textData: $('#textInputItem').html()
        }

        $.ajax({
            type: "POST",
            url: "/user/text_analysis_ua",
            contentType : 'application/json; charset=utf-8',
            dataType: "json",
            data: JSON.stringify(textInputDto),
            success: function(result) {
                processOutput(result);
                $("#callAjaxButton").attr("disabled", false);
            },
            error: function() {
                $("#callAjaxButton").attr("disabled", false);
                alert("Error");
            }
        });
    });

    function processOutput(output) {
        if (output.error != null) {
            alert("Error");
        } else {
            if (output.usagesOutput != null) {
                var usagesList = "";

                Object.keys(output.usagesOutput).forEach(function(key) {
                    usagesList = usagesList + "<li>" + key + " : " + output.usagesOutput[key] + "</li>";
                });

                $('#usagesOutput').html(usagesList);
            }

            if (output.markedOutput != null) {
                $('#textInputItem').html(output.markedOutput);
            }
        }
    }
});