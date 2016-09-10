function doPagination(urlToGetCount, urlToGetItems, firstResultParameterName, maxResultParameterName, callback) {

        var firstResult = 0;
        var maxResults = 10;
        var currentPage = 1;
        var count;

        var template = '<div id="maxResults">' +
            '<input class="max-results-input" type="text" value="10">' +
            '<span class="max-results-input"></span> ' +
            '<input type="button">' +
            '</div>' +
            '<div id="pagesNumbers"></div>';

        function setup($element, nameOfMaxResultsButton) {
            $element.append(template);
            $('input[type=button]', '#maxResults').attr('value', nameOfMaxResultsButton).click(function () {
                var $errorSpan = $('span.max-results-input');
                $errorSpan.text('');
                var value = parseInt($('input.max-results-input').val());
                if (isNaN(value) || value < 0) {
                    $errorSpan.text('should be a positive number')
                } else {
                    maxResults = value;
                    if (currentPage*maxResults > count) {
                        currentPage = Math.ceil(count / maxResults);
                    }
                    setPagesNumbers();
                    getItems();
                }

            });
            setPagesNumbers();
            getItems();
        }

        function setPagesNumbers() {
            $.get(urlToGetCount, undefined, function (response) {
                count = response;
                $(document).trigger('countLoaded');
            }, 'text');
            $(document).on('countLoaded', function (){
                var $pagesNumbers = $('#pagesNumbers');
                $pagesNumbers.text('');
                var numbersOfPages = getNumbersOfPages();
                $.each(numbersOfPages, function (index, element) {
                    var item;
                    if (element == currentPage) {
                        item = '<em>' + element + ' </em>';
                    } else {
                        item = '<button class="pageLink" id="' + element + '">' + element + ' </button>';
                    }
                    if (numbersOfPages.length - 1 === index) {
                        $pagesNumbers.append(item).children('.pageLink').click(function (event) {
                            event.preventDefault();
                            var target = event.target;
                            currentPage = target.id;
                            firstResult = (currentPage-1)* maxResults;
                            getItems();
                            setPagesNumbers();
                        });
                    } else {
                        $pagesNumbers.append(item)
                    }
                });
            })
        }

        function getItems() {
            var parameters = firstResultParameterName + "=" + firstResult + "&" + maxResultParameterName + '=' + maxResults;
            $.ajax(urlToGetItems, {
                method : 'GET',
                data : parameters,
                dataType : 'json',
                success : function (response) {
                    callback(response)
                }
            })
        }

        function getNumbersOfPages() {

            var rangeOfLeftSide = 2;
            var rangeOfRightSide = 2;
            var numbersOfPages = [];
            numbersOfPages[0] = 1;
            for (var pageNumber = currentPage - rangeOfLeftSide - 1; rangeOfLeftSide > 0; rangeOfLeftSide--) {
                pageNumber++;
                if (pageNumber < 2) {
                    rangeOfRightSide++;
                } else {
                    numbersOfPages.push(pageNumber);
                }
            }
            var lastPageNumber = Math.ceil(count / maxResults);
            if (currentPage != 1 && currentPage != lastPageNumber) {
                numbersOfPages.push(currentPage);
            } else {
                rangeOfRightSide++;
            }
            for (var pageNumber = currentPage; rangeOfRightSide > 0; rangeOfRightSide--) {
                pageNumber++;
                if (pageNumber < lastPageNumber) {
                    numbersOfPages.push(pageNumber);
                }
            }
            numbersOfPages.push(lastPageNumber);

            return numbersOfPages;
        }
        setup($('#pagination-container'), 'count');
}
