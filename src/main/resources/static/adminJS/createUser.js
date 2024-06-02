const form_new = document.getElementById('formForNewUser');
const role_new = document.querySelector('#roles').selectedOptions;

form_new.addEventListener('submit', addNewUser);

async function addNewUser(event) {
    event.preventDefault();
    const urlNew = '/api/admin/saveUser';
    let listOfRole = [];
    for (let i = 0; i < role_new.length; i++) {
        listOfRole.push({
            id: role_new[i].value
        });
    }
    let method = {
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: form_new.name.value,
            surname: form_new.surname.value,
            age: form_new.age.value,
            username: form_new.username.value,
            password: form_new.password.value,
            roles: listOfRole
        })
    }
    await fetch(urlNew, method).then(() => {
        form_new.reset();
        getAdminPage();
        var triggerTabList = [].slice.call(document.querySelectorAll('#Admin_panel-tab a'))
        triggerTabList.forEach(function (triggerEl) {
            var tabTrigger = new bootstrap.Tab(triggerEl)
            triggerEl.addEventListener('click', function (event) {
                event.preventDefault()
                tabTrigger.show()
            })
        })
        var triggerEl = document.querySelector('#Admin_panel-tab a[href="#user_table"]')
        bootstrap.Tab.getInstance(triggerEl).show() // Select tab by name
    });
}