ParticipActApp.controller('SidebarCtrl', function ($scope, $timeout, $localStorage) {
    if (!isBlank($localStorage.ParticipActSidebar)) {
        $('body').addClass('page-sidebar-closed');
        $('#pa-page-sidebar-menu').addClass('page-sidebar-menu-closed');
    }

    $scope.onClickToggler = function () {
        $timeout(function () {
            if ($(".page-sidebar-closed").length) {
                console.log('Hide');
                //$("body").addClass('page-sidebar-closed');
                $localStorage.ParticipActSidebar = true;
            } else {
                console.log('Show');
                //$("body").removeClass('page-sidebar-closed');
                delete $localStorage.ParticipActSidebar;
            }
        }, 500);
    };
});