$(function () {
    $('#addKeywordButton').click(function () {
        const keywordInput = $('#keywordInput');
        const keywordsArea = $('#Title\\:keywordsArea');

        const keyword = keywordInput.val().trim();

        if (keyword !== '') {
            const currentKeywords = keywordsArea.val().trim();
            const newKeywords = currentKeywords === '' ? keyword : `${currentKeywords}, ${keyword}`;
            keywordsArea.val(newKeywords);
            keywordInput.val('');
        }
    });

    $('#keywordInput').keydown(function (event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            $('#addKeywordButton').click();
        }
    });
});