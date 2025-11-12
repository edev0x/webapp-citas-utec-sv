<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="dateUtils" uri="http://utec.edu/jsp/tlds/dateUtils" %>

<t:layout isPublic="false" isError="false" isLogin="false" showSidebar="true">
  <div class="mx-auto max-w-(--breakpoint-2xl) p-4 pb-20 md:p-6 md:pb-6">
    <div class="rounded-2xl border border-gray-200 bg-white p-5 dark:border-gray-700 dark:bg-gray-800 md:p-6">
      <div id="calendar" class="min-h-screen w-full"></div>
    </div>
  </div>
  <jsp:include page="/WEB-INF/views/partials/appointments/create-appointment-dialog.jsp" />
  <jsp:include page="/WEB-INF/views/partials/appointments/edit-appointment-dialog.jsp" />
  <jsp:include page="/WEB-INF/views/partials/appointments/delete-appointment-dialog.jsp" />
</t:layout>