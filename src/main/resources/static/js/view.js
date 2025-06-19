console.log("Hi, this is view.js");

const $targetEl = document.getElementById('view_contact_modal');

const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => console.log('Modal is hidden'),
    onShow: () => console.log('Modal is shown'),
    onToggle: () => console.log('Modal has been toggled'),
};

const instanceOptions = {
    id: 'view_contact_modal',
    override: true
};

// ✅ Use $targetEl instead of undefined variable
const contactModal = new Modal($targetEl, options, instanceOptions);

// ✅ Call show() via button click
function openContactModal() {
    contactModal.show();
}

function closeContact() {
    contactModal.hide();
}

async function loadContact(id) {
    console.log(id);
    try {
        const data = await (await fetch(`http://localhost:8081/api/contacts/${id}`)).json();
        console.log(data);
        document.querySelector("#contact_name").innerHTML = data.name;
        document.querySelector("#contact_email").innerHTML = data.email;
        document.querySelector("#contact_phoneNumber").innerHTML = data.phoneNumber;
        document.querySelector("#contact_address").innerHTML = data.address;
        document.querySelector("#contact_about").innerHTML = data.about;
        document.querySelector("#contact_linkdin").textContent = data.linkedInLink;
        document.querySelector("#contact_linkdin").href = data.linkedInLink;

        document.querySelector("#contact_webSite").textContent = data.webSiteLink;
        document.querySelector("#contact_webSite").href = data.webSiteLink;

        document.querySelector("#contactImage").src = data.picture;

        openContactModal();

    } catch (error) {
        console.log("Error:", error);

    }


}
