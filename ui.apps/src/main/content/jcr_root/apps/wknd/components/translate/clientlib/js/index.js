(() => {

    document.addEventListener('DOMContentLoaded', function () {

    });

    trans = function() {
        var query = $('#trans-content').val();
        var component = $('.cmp-translate');
        var appKey = component.attr('appId');
        var key = component.attr('appKey');
        var salt = (new Date).getTime();
        var curtime = Math.round(new Date().getTime()/1000);
        var to = 'zh-CHS';
        var from = 'en';
        var str1 = appKey + truncate(query) + salt + curtime + key;
        // 用户词表ID
        var vocabId =  '';
        var sign = CryptoJS.SHA256(str1).toString(CryptoJS.enc.Hex);
        $.ajax({
            url: 'http://openapi.youdao.com/api',
            type: 'post',
            dataType: 'jsonp',
            data: {
                q: query,
                appKey: appKey,
                salt: salt,
                from: from,
                to: to,
                sign: sign,
                signType: "v3",
                curtime: curtime,
                vocabId: vocabId,
            },
            success: function (data) {
                var translation = data.translation[0]
                $('#result').html('翻译结果：' + translation);
            }
        });
    }

    const truncate = (q)=> {
        var len = q.length;
        if(len<=20) return q;
        return q.substring(0, 10) + len + q.substring(len-10, len);
    }

    transByServlet = function() {
        var query = $('#trans-content').val();
        $.ajax({
            url: '/bin/translate?content=' + query,
            type: 'get',
            success: function (data) {
                var translation = data.translation
                $('#result').html('翻译结果：' + translation);
            }
        });
    }

})();