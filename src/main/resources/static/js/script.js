/**
 * Student Management System - JavaScript
 */

// Initialize when DOM is ready
document.addEventListener('DOMContentLoaded', function() {
    initializeFormValidation();
    initializeTooltips();
    setupDeleteConfirmation();
});

/**
 * Initialize Bootstrap form validation
 */
function initializeFormValidation() {
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        });
    });
}

/**
 * Initialize Bootstrap tooltips
 */
function initializeTooltips() {
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
}

/**
 * Setup delete confirmation with additional checks
 */
function setupDeleteConfirmation() {
    const deleteButtons = document.querySelectorAll('[data-bs-target*="deleteModal"]');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function() {
            // Add any pre-deletion logic here if needed
            console.log('Delete modal will be shown');
        });
    });
}

/**
 * Format currency for display
 */
function formatCurrency(amount) {
    return new Intl.NumberFormat('en-US', {
        style: 'currency',
        currency: 'USD'
    }).format(amount);
}

/**
 * Format date to readable format
 */
function formatDate(date) {
    return new Intl.DateTimeFormat('en-US', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
    }).format(new Date(date));
}

/**
 * Show flash message
 */
function showFlashMessage(message, type = 'info', duration = 5000) {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
    alertDiv.role = 'alert';
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    
    const container = document.querySelector('main') || document.body;
    container.insertBefore(alertDiv, container.firstChild);
    
    if (duration > 0) {
        setTimeout(() => {
            alertDiv.remove();
        }, duration);
    }
}

/**
 * Confirm action before proceeding
 */
function confirmAction(message) {
    return confirm(message);
}

/**
 * Debounce function for search/filter
 */
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

/**
 * Search filter for student list
 */
function filterStudents(searchTerm) {
    const cards = document.querySelectorAll('.card-modern');
    cards.forEach(card => {
        const text = card.textContent.toLowerCase();
        if (text.includes(searchTerm.toLowerCase())) {
            card.style.display = '';
        } else {
            card.style.display = 'none';
        }
    });
}

/**
 * Calculate GPA color based on value
 */
function getGPAColor(gpa) {
    if (gpa >= 3.5) return '#10b981'; // Success green
    if (gpa >= 3.0) return '#3b82f6'; // Info blue
    if (gpa >= 2.0) return '#f59e0b'; // Warning yellow
    return '#ef4444'; // Danger red
}

/**
 * Show loading spinner
 */
function showLoading() {
    const spinner = document.createElement('div');
    spinner.className = 'spinner-border text-primary';
    spinner.role = 'status';
    return spinner;
}

/**
 * Handle form submission with loading state
 */
function handleFormSubmit(form) {
    const submitButton = form.querySelector('button[type="submit"]');
    if (submitButton) {
        submitButton.disabled = true;
        submitButton.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Loading...';
    }
}

/**
 * Reset form to initial state
 */
function resetFormLoading(form) {
    const submitButton = form.querySelector('button[type="submit"]');
    if (submitButton) {
        submitButton.disabled = false;
        submitButton.innerHTML = 'Submit';
    }
}

/**
 * Export utility for future use
 */
window.StudentApp = {
    formatCurrency,
    formatDate,
    showFlashMessage,
    confirmAction,
    debounce,
    filterStudents,
    getGPAColor,
    showLoading,
    handleFormSubmit,
    resetFormLoading
};
