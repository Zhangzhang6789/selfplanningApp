<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf" %>
<div class="container">
    <h1>Your Todos</h1>
    <table class="table">
        <thead>
        <tr>
            <th>Description</th>
            <th>Target Date</th>
            <th>Is Done?</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${todoss}" var="todo">
            <tr>
                <td>${todo.description}</td>
                <td>${todo.targetDate}</td>
                <td>${todo.done}</td>
                <td> <a href="delete-todo?id=${todo.id}" class="btn btn-dark">DELETE</a> </td>
                <td> <a href="update-todo?id=${todo.id}" class="btn btn-success">Update</a> </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="addtodo" class="btn btn-success">Add To Do</a>
</div>
<%@ include file="common/footer.jspf"%>