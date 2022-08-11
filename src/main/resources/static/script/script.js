$(function(){
    console.log("log here ")
    $("a.confirmDeletion").click(function(){
        console.log("console.log here ")
        if(!confirm("Confirm Deletion"))
        {
            return false;
        }
    });
    if($("#content").length){
        ClassicEditor.create(document.querySelector("#content"))
            .catch(error=> console.log(error));
    }
});
function readURL(input, idNum){
    if(input.files&&input.files[0]){
        let reader = new FileReader();
        reader.onload=function(e){
            $("#imgPreview"+idNum).attr("src", e.target.result).height(100);

        }
        reader.readAsDataURL(input.files[0]);
    }
}