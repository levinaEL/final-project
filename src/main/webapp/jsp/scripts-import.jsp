<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<%--<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>--%>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../app/assets/js/bootstrap.min.js"></script>
<script src="../app/assets/js/bootstrap-datepicker.min.js"></script>

<script type="application/javascript">
    $(function () {
        $('[data-lang]').click(function () {
            $('[data-lang-target]').val($(this).data('lang'));
            document.langForm.submit();
        });
    });
</script>
<script>
    var form = document.getElementById('book');
    document.getElementById('create').onclick = function () {
        form.target = '_self';
        form.submit();
    };
</script>
