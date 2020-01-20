$(document).ready(function() {
    $("#callAjaxButton").click(function(e) {
        e.preventDefault();

        var textInputDto = {
           textData: $('#textInputItem').val()
        }

        $.ajax({
            type: "POST",
            url: "/user/text_analysis_ua",
            contentType : 'application/json; charset=utf-8',
            dataType: "json",
            data: JSON.stringify(textInputDto),
            success: function(result) {
                processOutput(result)
            },
            error: function() {
                alert("Error")
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
                $('#markedOutput').html(output.markedOutput);
            }
        }
    }
});